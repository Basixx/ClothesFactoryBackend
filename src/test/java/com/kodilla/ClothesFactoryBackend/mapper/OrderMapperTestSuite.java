package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Fedex;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTestSuite {
    @InjectMocks
    private OrderMapper orderMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClothMapper clothMapper;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testMapToOrderDto() {
        //Given
        User user = createUser();

        Order order = createOrder(1L, user, new BigDecimal(30));
        when(clothMapper.mapToClothesIdsFromClothes(anyList())).thenReturn(new ArrayList<>());

        //When
        OrderDto orderDto = orderMapper.mapToOrderDto(order);

        //Then
        assertEquals(1L, orderDto.getId());
        assertEquals(LocalDate.of(2022, 4, 22), orderDto.getOrderDate());
        assertEquals(new BigDecimal(30), orderDto.getTotalOrderPrice());
        assertTrue(orderDto.isPaid());
        assertFalse(orderDto.isSent());
        assertEquals(order.getShipmentCompany(), orderDto.getShipmentCompany());
        assertEquals(new BigDecimal(20), orderDto.getShippingPrice());
        assertEquals("Marszalkowska, 1/2, Warsaw, 00-111", orderDto.getAddress());
        assertEquals(3L, orderDto.getUserId());
        assertEquals(0, orderDto.getClothesIdList().size());
    }

    @Test
    public void testMapToOrderDtoList() {
        //Given
        User user = createUser();
        Order order1 = createOrder(2L, user, new BigDecimal(20));
        Order order2 = createOrder(3L, user, new BigDecimal(50));

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        //When
        List<OrderDto> ordersDto = orderMapper.mapToOrderDtoList(orders);

        //Then
        assertEquals(2, ordersDto.size());
        assertEquals(2L, ordersDto.get(0).getId());
        assertEquals(3L, ordersDto.get(1).getId());
        assertEquals(new BigDecimal(20), ordersDto.get(0).getTotalOrderPrice());
        assertEquals(new BigDecimal(50), ordersDto.get(1).getTotalOrderPrice());
    }

    @Test
    public void testMapToOrdersFromIds() throws OrderNotFoundException {
        //Given
        List<Long> ordersIds = new ArrayList<>();
        ordersIds.add(4L);
        ordersIds.add(5L);

        User user = createUser();

        Order order1 = createOrder(4L, user, new BigDecimal(30));
        Order order2 = createOrder(5L, user, new BigDecimal(100));

        when(orderRepository.findById(4L)).thenReturn(Optional.of(order1));
        when(orderRepository.findById(5L)).thenReturn(Optional.of(order2));

        //When
        List<Order> orders = orderMapper.mapToOrdersFromIds(ordersIds);

        //Then
        assertEquals(2, orders.size());
        assertEquals(4L, orders.get(0).getId());
        assertEquals(5L, orders.get(1).getId());
        assertEquals(new BigDecimal(30), orders.get(0).getTotalOrderPrice());
        assertEquals(new BigDecimal(100), orders.get(1).getTotalOrderPrice());
    }

    @Test
    public void testMapToOrdersIdsFromOrders() {
        //Given
        User user = createUser();

        Order order1 = createOrder(5L, user, new BigDecimal(50));
        Order order2 = createOrder(6L, user, new BigDecimal(200));
        Order order3 = createOrder(7L, user, new BigDecimal(70));

        List<Order> orders = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        //When
        List<Long> ids = orderMapper.mapToOrdersIdsFromOrders(orders);

        //Then
        assertEquals(3, ids.size());
        assertEquals(5L, ids.get(0));
        assertEquals(6L, ids.get(1));
        assertEquals(7L, ids.get(2));
    }

    private User createUser() {
        return User.builder()
                .id(3L)
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .street("Marszalkowska")
                .streetAndApartmentNumber("1/2")
                .city("Warsaw")
                .postCode("00-111")
                .build();
    }

    private Order createOrder(Long id, User user, BigDecimal price) {

        String address = user.toString();
        ShipmentCompany fedex = new Fedex();

        return Order.builder()
                .id(id)
                .orderDate(LocalDate.of(2022, 4, 22))
                .totalOrderPrice(price)
                .paid(true)
                .sent(false)
                .shipmentCompany(fedex)
                .shippingPrice(new BigDecimal(20))
                .address(address)
                .user(user)
                .clothesList(new ArrayList<>())
                .build();
    }
}