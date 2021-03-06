package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.auxiliary.Prices;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothMapper {
    private final ClothRepository clothRepository;
    private final Prices prices = new Prices();

    public Cloth mapToCloth(final ClothDto clothDto) {
        return Cloth.builder()
                .fashion(clothDto.getFashion())
                .color(clothDto.getColor())
                .print(clothDto.getPrint())
                .font(clothDto.getFont())
                .printColor(clothDto.getPrintColor())
                .size(clothDto.getSize())
                .quantity(clothDto.getQuantity())
                .price(prices.findClothPrice(clothDto.getFashion()).multiply(BigDecimal.valueOf(clothDto.getQuantity())))
                .build();
    }

    public ClothDto mapToClothDto(final Cloth cloth) {
        return ClothDto.builder()
                .id(cloth.getId())
                .fashion(cloth.getFashion())
                .color(cloth.getColor())
                .print(cloth.getPrint())
                .font(cloth.getFont())
                .printColor(cloth.getPrintColor())
                .size(cloth.getSize())
                .quantity(cloth.getQuantity())
                .price(cloth.getPrice())
                .build();
    }

    public List<ClothDto> mapToClothDtoList(final List<Cloth> cloths) {
        return cloths.stream()
                .map(this::mapToClothDto)
                .collect(Collectors.toList());
    }

    public List<Cloth> mapToClothesFromIds(final List<Long> clothesIds) throws ClothNotFoundException {
        List<Cloth> clothes = new ArrayList<>();

        if(clothesIds.size() > 0) {
            for (Long clothId : clothesIds) {
                Cloth cloth = clothRepository.findById(clothId).orElseThrow(ClothNotFoundException::new);
                clothes.add(cloth);
            }
        }
        return clothes;
    }

    public List<Long> mapToClothesIdsFromClothes(final List<Cloth> clothes) {
        List<Long> clothesIds = new ArrayList<>();
        if (clothes.size() > 0) {
            clothesIds = clothes.stream()
                    .map(Cloth::getId)
                    .collect(Collectors.toList());
        }
        return clothesIds;
    }
}