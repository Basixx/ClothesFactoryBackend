package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.CartDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.clothes.factory.object_mother.CartMother.createCart;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CartMapperTests {

    @InjectMocks
    private CartMapper cartMapper;

    @Mock
    private ClothMapper clothMapper;

    @Test
    void testMapToCartDto() {
        //Given
        Cart cart = createCart(new BigDecimal(300));

        //When
        CartDto cartDto = cartMapper.mapToCartDto(cart);

        //When
        assertEquals(9L, cartDto.getId());
        assertEquals(new BigDecimal(300), cartDto.getTotalPrice());
        assertEquals(0, cartDto.getClothesIdList().size());
    }

}
