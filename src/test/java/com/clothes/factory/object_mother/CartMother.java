package com.clothes.factory.object_mother;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.CartDto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartMother {

    public static Cart createCart(BigDecimal price) {
        return Cart.builder()
                .id(9L)
                .totalPrice(price)
                .clothesList(new ArrayList<>())
                .build();
    }

    public static CartDto createCartDto() {
        return CartDto.builder()
                .id(1L)
                .totalPrice(new BigDecimal(100))
                .clothesIdList(new ArrayList<>())
                .build();
    }

}
