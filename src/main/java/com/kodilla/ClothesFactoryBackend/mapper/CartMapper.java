package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {
    private final ClothMapper clothMapper;

    public CartDto mapToCartDto(final Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .clothesIdList(clothMapper.mapToClothesIdsFromClothes(cart.getClothesList()))
                .build();
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> carts) {
        return carts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }
}