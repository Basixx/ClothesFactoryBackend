package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.User;
import com.clothes.factory.domain.UserDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.object_mother.CartMother;
import com.clothes.factory.object_mother.UserMother;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTestSuite {
    @InjectMocks
    private UserMapper userMapper;

    @Mock
    CartRepository cartRepository;

    @Mock
    OrderMapper orderMapper;

    @Test
    void testMapToUser() throws OrderNotFoundException, CartNotFoundException {
        //Given
        UserDto userDto = UserMother.createUserDto();

        Cart cart = CartMother.createCart(new BigDecimal(40));

        when(cartRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cart));
        when(orderMapper.mapToOrdersFromIds(anyList())).thenReturn(new ArrayList<>());

        //When
        User user = userMapper.mapToUser(userDto);

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
    void testMapToUserDto() {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(40));
        User user = UserMother.createUser1();
        user.setCart(cart);

        when(orderMapper.mapToOrdersIdsFromOrders(anyList())).thenReturn(new ArrayList<>());

        //When
        UserDto userDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(6L, userDto.getId());
        assertEquals("John", userDto.getName());
        assertEquals("Smith", userDto.getSurname());
        assertEquals("111111111", userDto.getPhoneNumber());
        assertEquals("john@smith.com", userDto.getEmailAddress());
        assertEquals("password1", userDto.getPassword());
        assertEquals(9L, userDto.getCartId());
        assertEquals(0, userDto.getOrdersIdList().size());
    }

    @Test
    void testMapToUserDtoList() {
        //Given
        Cart cart1 = CartMother.createCart( new BigDecimal(150));
        Cart cart2 = CartMother.createCart( new BigDecimal(250));
        cart2.setId(10L);
        User user1 = UserMother.createUser1();
        user1.setCart(cart1);
        User user2 = UserMother.createUser2();
        user2.setCart(cart2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        //When
        List<UserDto> usersDto = userMapper.mapToUserDtoList(users);

        //Then
        assertEquals(2, usersDto.size());
        assertEquals(6L, usersDto.get(0).getId());
        assertEquals(7L, usersDto.get(1).getId());
        assertEquals(9L, usersDto.get(0).getCartId());
        assertEquals(10L, usersDto.get(1).getCartId());
    }
}