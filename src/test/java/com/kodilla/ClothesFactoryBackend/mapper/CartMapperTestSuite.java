package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CartMapperTestSuite {
    private CartMapper cartMapper = new CartMapper();


    //DODAĆ MOCKA!!!!!!!!!!!
//    @Test
//    public void testMapToCart() throws ClothNotFoundException {
//        //Given
//        CartDto cartDto = CartDto.builder()
//                .totalPrice(new BigDecimal(150))
//                .clothesIdList(new ArrayList<>())
//                .build();
//
//        //When
//        Cart cart = cartMapper.mapToCart(cartDto);
//
//        //Then
//        assertEquals(new BigDecimal(150), cart.getTotalPrice());
//        assertEquals(0, cart.getClothesList().size());
//    }

}
