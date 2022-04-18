package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClothMapper {
    private final ClothRepository clothRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public Cloth mapToCloth(final ClothDto clothDto) throws CartNotFoundException, OrderNotFoundException {
        return Cloth.builder()
                .id(clothDto.getId())
                .type(clothDto.getType())
                .color(clothDto.getColor())
                .print(clothDto.getPrint())
                .font(clothDto.getFont())
                .printColor(clothDto.getPrintColor())
                .quantity(clothDto.getQuantity())
                .price(clothDto.getPrice())
                .cart(cartRepository.findById(clothDto.getCartId()).orElseThrow(CartNotFoundException::new))
                .order(orderRepository.findById(clothDto.getOrderId()).orElseThrow(OrderNotFoundException::new))
                .build();
    }

    public ClothDto mapToClothDto(final Cloth cloth) {
        return ClothDto.builder()
                .id(cloth.getId())
                .type(cloth.getType())
                .color(cloth.getColor())
                .print(cloth.getPrint())
                .font(cloth.getFont())
                .printColor(cloth.getPrintColor())
                .quantity(cloth.getQuantity())
                .price(cloth.getPrice())
                .cartId(cloth.getCart().getId())
                .orderId(cloth.getOrder().getId())
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
                clothes.add(clothRepository.findById(clothId).orElseThrow(ClothNotFoundException::new));
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