package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.User;
import com.clothes.factory.domain.UserRequestDto;
import com.clothes.factory.domain.UserResponseDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clothes.factory.object_mother.CartMother.createCart;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static com.clothes.factory.object_mother.UserMother.createUser2;
import static com.clothes.factory.object_mother.UserMother.createUserRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    @Mock
    CartRepository cartRepository;
    @Mock
    OrderMapper orderMapper;
    @InjectMocks
    private UserMapper userMapper;

    @Test
    void testMapToUser() throws OrderNotFoundException, CartNotFoundException {
        //Given
        UserRequestDto userRequestDto = createUserRequestDto();

        Cart cart = createCart(new BigDecimal(40));

        when(cartRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(cart));
        when(orderMapper.mapToOrdersFromIds(anyList()))
                .thenReturn(new ArrayList<>());

        //When
        User user = userMapper.mapToUser(userRequestDto);

        Long cartId = user.getCart().getId();
        //Then
        assertEquals("John", user.getName());
        assertEquals("Smith", user.getSurname());
        assertEquals("111222333", user.getPhoneNumber());
        assertEquals("john@smith.com", user.getEmailAddress());
        assertEquals("password", user.getPassword());
        assertEquals(9L, cartId);
        assertEquals(0, user.getOrdersList().size());
    }

    @Test
    void testMapToUserResponseDto() {
        //Given
        Cart cart = createCart(new BigDecimal(40));
        User user = createUser1();
        user.setCart(cart);

        when(orderMapper.mapToOrdersIdsFromOrders(anyList()))
                .thenReturn(new ArrayList<>());

        //When
        UserResponseDto userResponseDto = userMapper.mapToUserResponseDto(user);

        //Then
        assertEquals(6L, userResponseDto.getId());
        assertEquals("John", userResponseDto.getName());
        assertEquals("Smith", userResponseDto.getSurname());
        assertEquals("111111111", userResponseDto.getPhoneNumber());
        assertEquals("john@smith.com", userResponseDto.getEmailAddress());
        assertEquals(9L, userResponseDto.getCartId());
        assertEquals(0, userResponseDto.getOrdersIdList().size());
    }

    @Test
    void testMapToUserRequestDtoList() {
        //Given
        Cart cart1 = createCart(new BigDecimal(150));
        Cart cart2 = createCart(new BigDecimal(250));
        cart2.setId(10L);
        User user1 = createUser1();
        user1.setCart(cart1);
        User user2 = createUser2();
        user2.setCart(cart2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        //When
        List<UserResponseDto> usersDto = userMapper.mapToUserDtoList(users);

        //Then
        assertEquals(2, usersDto.size());
        assertEquals(6L, usersDto.getFirst().getId());
        assertEquals(7L, usersDto.get(1).getId());
        assertEquals(9L, usersDto.getFirst().getCartId());
        assertEquals(10L, usersDto.get(1).getCartId());
    }

}
