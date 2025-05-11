package com.clothes.factory.mapper;

import com.clothes.factory.auxiliary.shipment.strategy.companies.Dhl;
import com.clothes.factory.auxiliary.shipment.strategy.companies.Fedex;
import com.clothes.factory.auxiliary.shipment.strategy.companies.InPost;
import com.clothes.factory.auxiliary.shipment.strategy.companies.Ups;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.OrderDto;
import com.clothes.factory.domain.User;
import com.clothes.factory.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.clothes.factory.object_mother.OrderMother.createOrder;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTests {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private ClothMapper clothMapper;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testMapToOrderDto() {
        //Given
        User user = createUser1();

        Order order = createOrder(1L, user, new BigDecimal(30), new Dhl());
        when(clothMapper.mapToClothesIdsFromClothes(anyList()))
                .thenReturn(new ArrayList<>());

        //When
        OrderDto orderDto = orderMapper.mapToOrderDto(order);

        //Then
        assertEquals(1L, orderDto.getId());
        assertEquals(LocalDate.of(2022, 4, 22), orderDto.getOrderDate());
        assertEquals(new BigDecimal(30), orderDto.getTotalOrderPrice());
        assertTrue(orderDto.isPaid());
        assertFalse(orderDto.isSent());
        assertEquals(new BigDecimal(20), orderDto.getShippingPrice());
        assertEquals("Marszalkowska, 1/2, Warsaw, 00-111", orderDto.getAddress());
        assertEquals(6L, orderDto.getUserId());
        assertEquals("john@smith.com", orderDto.getUserMail());
        assertEquals(0, orderDto.getClothesIdList().size());
    }

    @Test
    void testMapToOrderDtoList() {
        //Given
        User user = createUser1();
        Order order1 = createOrder(2L, user, new BigDecimal(20), new Fedex());
        Order order2 = createOrder(3L, user, new BigDecimal(50), new Ups());

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        //When
        List<OrderDto> ordersDto = orderMapper.mapToOrderDtoList(orders);

        //Then
        assertEquals(2, ordersDto.size());

        assertEquals(2L, ordersDto.getFirst().getId());
        assertEquals(LocalDate.of(2022, 4, 22), ordersDto.getFirst().getOrderDate());
        assertEquals(new BigDecimal(20), ordersDto.getFirst().getTotalOrderPrice());
        assertTrue(ordersDto.getFirst().isPaid());
        assertFalse(ordersDto.getFirst().isSent());
        assertEquals(new BigDecimal(20), ordersDto.getFirst().getShippingPrice());
        assertEquals("Marszalkowska, 1/2, Warsaw, 00-111", ordersDto.getFirst().getAddress());
        assertEquals(6L, ordersDto.getFirst().getUserId());
        assertEquals("john@smith.com", ordersDto.getFirst().getUserMail());
        assertEquals(0, ordersDto.getFirst().getClothesIdList().size());

        assertEquals(3L, ordersDto.get(1).getId());
        assertEquals(LocalDate.of(2022, 4, 22), ordersDto.get(1).getOrderDate());
        assertEquals(new BigDecimal(50), ordersDto.get(1).getTotalOrderPrice());
        assertTrue(ordersDto.get(1).isPaid());
        assertFalse(ordersDto.get(1).isSent());
        assertEquals(new BigDecimal(20), ordersDto.get(1).getShippingPrice());
        assertEquals("Marszalkowska, 1/2, Warsaw, 00-111", ordersDto.get(1).getAddress());
        assertEquals(6L, ordersDto.get(1).getUserId());
        assertEquals("john@smith.com", ordersDto.get(1).getUserMail());
        assertEquals(0, ordersDto.get(1).getClothesIdList().size());
    }

    @Test
    void testMapToOrdersIdsFromOrders() {
        //Given
        User user = createUser1();

        Order order1 = createOrder(5L, user, new BigDecimal(50), new InPost());
        Order order2 = createOrder(6L, user, new BigDecimal(200), new Dhl());
        Order order3 = createOrder(7L, user, new BigDecimal(70), new Fedex());

        List<Order> orders = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        //When
        List<Long> ids = orderMapper.mapToOrdersIdsFromOrders(orders);

        //Then
        assertEquals(3, ids.size());
        assertEquals(5L, ids.getFirst());
        assertEquals(6L, ids.get(1));
        assertEquals(7L, ids.get(2));
    }

}
