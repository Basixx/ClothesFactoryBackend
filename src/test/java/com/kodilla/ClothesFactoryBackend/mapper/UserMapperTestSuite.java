package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
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
    public void testMapToUser() throws OrderNotFoundException, CartNotFoundException {
        //Given
        UserDto userDto = UserDto.builder()
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .cartId(2L)
                .ordersIdList(new ArrayList<>())
                .build();

        Cart cart = createCart(2L, new BigDecimal(40));

        when(cartRepository.findById(2L)).thenReturn(Optional.ofNullable(cart));
        when(orderMapper.mapToOrdersFromIds(anyList())).thenReturn(new ArrayList<>());

        //When
        User user = userMapper.mapToUser(userDto);

        Long cartId = user.getCart().getId();
        //Then
        assertEquals("John", user.getName());
        assertEquals("Smith", user.getSurname());
        assertEquals("111111111", user.getPhoneNumber());
        assertEquals("john@smith.com", user.getEmailAddress());
        assertEquals("password1", user.getPassword());
        assertEquals(2L, cartId);
        assertEquals(0, user.getOrdersList().size());
    }

    @Test
    public void testMapToUserDto() {
        //Given
        Cart cart = createCart(2L, new BigDecimal(40));
        User user = createUser(3L, cart);

        when(orderMapper.mapToOrdersIdsFromOrders(anyList())).thenReturn(new ArrayList<>());

        //When
        UserDto userDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(3L, userDto.getId());
        assertEquals("John", userDto.getName());
        assertEquals("Smith", userDto.getSurname());
        assertEquals("111111111", userDto.getPhoneNumber());
        assertEquals("john@smith.com", userDto.getEmailAddress());
        assertEquals("password1", userDto.getPassword());
        assertEquals(2L, userDto.getCartId());
        assertEquals(0, userDto.getOrdersIdList().size());
    }

    @Test
    public void testMapToUserDtoList() {
        //Given
        Cart cart1 = createCart(4L, new BigDecimal(150));
        Cart cart2 = createCart(5L, new BigDecimal(250));
        User user1 = createUser(6L, cart1);
        User user2 = createUser(7L, cart2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        //When
        List<UserDto> usersDto = userMapper.mapToUserDtoList(users);

        //Then
        assertEquals(2, usersDto.size());
        assertEquals(6L, usersDto.get(0).getId());
        assertEquals(7L, usersDto.get(1).getId());
        assertEquals(4L, usersDto.get(0).getCartId());
        assertEquals(5L, usersDto.get(1).getCartId());
    }

    private User createUser(Long id, Cart cart) {
        return User.builder()
                .id(id)
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .cart(cart)
                .ordersList(new ArrayList<>())
                .build();
    }

    private Cart createCart(Long id, BigDecimal price) {
        return Cart.builder()
                .id(id)
                .totalPrice(price)
                .clothesList(new ArrayList<>())
                .build();
    }
}
