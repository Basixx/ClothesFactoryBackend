package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {
    private final ClothMapper clothMapper;

    public Cart mapToCart(final CartDto cartDto) throws ClothNotFoundException {
        return Cart.builder()
                .totalPrice(cartDto.getTotalPrice())
                .clothesList(cartDto.getClothesIdList() == null ? null : clothMapper.mapToClothesFromIds(cartDto.getClothesIdList()))
                .build();
    }

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