package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .toList();
    }

}
