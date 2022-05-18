package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CartMapperTestSuite {
    @InjectMocks
    private CartMapper cartMapper;

    @Mock
    private ClothMapper clothMapper;

    @Test
    void testMapToCartDto() {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(300));

        //When
        CartDto cartDto = cartMapper.mapToCartDto(cart);

        //When
        assertEquals(9L, cartDto.getId());
        assertEquals(new BigDecimal(300), cartDto.getTotalPrice());
        assertEquals(0, cartDto.getClothesIdList().size());
    }

    @Test
    void testMapToCartDtoList() {
        Cart cart1 = CartMother.createCart(new BigDecimal(300));

        Cart cart2 = CartMother.createCart(new BigDecimal(500));
        cart2.setId(10L);

        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        //When
        List<CartDto> cartDtoList = cartMapper.mapToCartDtoList(carts);

        //Then
        assertEquals(2, cartDtoList.size());
        assertEquals(9L, cartDtoList.get(0).getId());
        assertEquals(10L, cartDtoList.get(1).getId());
        assertEquals(new BigDecimal(300), cartDtoList.get(0).getTotalPrice());
        assertEquals(new BigDecimal(500), cartDtoList.get(1).getTotalPrice());
    }
}