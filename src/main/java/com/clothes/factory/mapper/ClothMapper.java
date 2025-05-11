package com.clothes.factory.mapper;

import com.clothes.factory.auxiliary.Prices;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.ClothDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothMapper {

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
                .price(prices
                        .findClothPrice(clothDto.getFashion())
                        .multiply(BigDecimal.valueOf(clothDto.getQuantity()))
                ).build();
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
                .toList();
    }

    public List<Long> mapToClothesIdsFromClothes(final List<Cloth> clothes) {
        return clothes.stream()
                .map(Cloth::getId)
                .toList();
    }

}
