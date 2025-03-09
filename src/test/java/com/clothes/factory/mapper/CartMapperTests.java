package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.CartDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testMapToCartDtoList() {
        Cart cart1 = createCart(new BigDecimal(300));

        Cart cart2 = createCart(new BigDecimal(500));
        cart2.setId(10L);

        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        //When
        List<CartDto> cartDtoList = cartMapper.mapToCartDtoList(carts);

        //Then
        assertEquals(2, cartDtoList.size());
        assertEquals(9L, cartDtoList.getFirst().getId());
        assertEquals(10L, cartDtoList.get(1).getId());
        assertEquals(new BigDecimal(300), cartDtoList.getFirst().getTotalPrice());
        assertEquals(new BigDecimal(500), cartDtoList.get(1).getTotalPrice());
    }

}
