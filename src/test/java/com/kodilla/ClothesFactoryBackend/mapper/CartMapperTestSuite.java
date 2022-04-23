package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CartMapperTestSuite {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void testMapToCart() throws ClothNotFoundException {
        //Given
        CartDto cartDto = CartDto.builder()
                .totalPrice(new BigDecimal(150))
                .clothesIdList(new ArrayList<>())
                .build();

        //When
        Cart cart = cartMapper
                .mapToCart(cartDto);

        //Then
        assertEquals(new BigDecimal(150), cart.getTotalPrice());
        assertEquals(0, cart.getClothesList().size());
    }

    @Test
    public void testMapToCartDto() {
        //Given
        Cart cart = Cart.builder()
                .totalPrice(new BigDecimal(300))
                .clothesList(new ArrayList<>())
                .build();

        //When
        CartDto cartDto = cartMapper.mapToCartDto(cart);

        //When
        assertEquals(new BigDecimal(300), cartDto.getTotalPrice());
        assertEquals(0, cartDto.getClothesIdList().size());
    }

    @Test
    public void testMapToCartDtoList() {
        Cart cart1 = Cart.builder()
                .totalPrice(new BigDecimal(300))
                .clothesList(new ArrayList<>())
                .build();

        Cart cart2 = Cart.builder()
                .totalPrice(new BigDecimal(500))
                .clothesList(new ArrayList<>())
                .build();

        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        //When
        List<CartDto> cartDtoList = cartMapper.mapToCartDtoList(carts);

        //Then
        assertEquals(2, cartDtoList.size());
        assertEquals(new BigDecimal(300), cartDtoList.get(0).getTotalPrice());
        assertEquals(new BigDecimal(500), cartDtoList.get(1).getTotalPrice());
    }
}
