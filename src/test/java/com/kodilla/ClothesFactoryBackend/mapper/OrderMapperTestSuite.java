package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

@SpringBootTest
@Transactional
public class OrderMapperTestSuite {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMapToOrder() throws UserNotFoundException, ClothNotFoundException {
        //Given
        User user = User.builder()
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .build();
        userRepository.save(user);
        Long userId = user.getId();

        OrderDto orderDto = OrderDto.builder()
                .orderDate(LocalDate.of(2022, 4, 22))
                .totalOrderPrice(new BigDecimal(30))
                .paid(true)
                .sent(false)
                .userId(userId)
                .clothesIdList(new ArrayList<>())
                .build();

        //When
        Order order = orderMapper.mapToOrder(orderDto);

        //Then
        assertEquals(LocalDate.of(2022, 4, 22), order.getOrderDate());
        assertEquals(new BigDecimal(30), order.getTotalOrderPrice());
        assertTrue(order.isPaid());
        assertFalse(order.isSent());
        assertEquals("John", order.getUser().getName());
        assertEquals(0, order.getClothesList().size());
    }

    @Test
    public void mapToOrderDto() {
        //Given
        User user = User.builder()
                .id(3L)
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .build();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2022, 4, 22))
                .totalOrderPrice(new BigDecimal(30))
                .paid(true)
                .sent(false)
                .user(user)
                .clothesList(new ArrayList<>())
                .build();

        //When
        OrderDto orderDto = orderMapper.mapToOrderDto(order);

        //Then
        assertEquals(LocalDate.of(2022, 4, 22), orderDto.getOrderDate());
        assertEquals(new BigDecimal(30), orderDto.getTotalOrderPrice());
        assertTrue(orderDto.isPaid());
        assertFalse(orderDto.isSent());
        assertEquals(3L, orderDto.getUserId());
        assertEquals(0, orderDto.getClothesIdList().size());
    }
}
