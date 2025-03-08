package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentMethod;
import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.cart.EmptyCartException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadyPaidException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadySentException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotPaidException;
import com.kodilla.ClothesFactoryBackend.facade.OrderFacade;
import com.kodilla.ClothesFactoryBackend.object_mother.OrderMother;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(OrderController.class)
class OrderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderFacade orderFacade;

    @Test
    void testGetOrder() throws Exception {
        //Given
        OrderDto orderDto = OrderMother.createOrderDto();

        when(orderFacade.getOrder(anyLong())).thenReturn(orderDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
        matchResult(resultActions, "$", 1, false, false, 2);
    }

    @Test
    void testGetAllOrders() throws Exception {
        //Given
        List<OrderDto> ordersDto = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ordersDto.add(OrderDto.builder()
                    .id(i + 1L)
                    .orderDate(LocalDate.of(2022, 4, 26))
                    .totalOrderPrice(new BigDecimal(100))
                    .paid(false)
                    .sent(false)
                    .shipmentCompanyName("InPost")
                    .shippingPrice(new BigDecimal(20))
                    .deliveryDays(3)
                    .address("Wilcza, 5/6, Warsaw, 02-234")
                    .userId(i + 2L)
                    .clothesIdList(new ArrayList<>())
                    .build());
        }

        when(orderFacade.getAllOrders()).thenReturn(ordersDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
        matchResult(resultActions, "$[0]", 1, false, false, 2);
        matchResult(resultActions, "$[1]", 2, false, false, 3);
    }

    @Test
    void testGetOrdersByUser() throws Exception {
        //Given
        List<OrderDto> orderDtoList = new ArrayList<>();
        OrderDto orderDto = OrderMother.createOrderDto();
        orderDtoList.add(orderDto);

        when(orderFacade.getOrdersByUser(anyLong())).thenReturn(orderDtoList);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders/byUser/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
        matchResult(resultActions, "$[0]", 1, false, false, 2);
    }

    @Test
    void testCreateOrder() throws Exception {
        //Given
        OrderDto orderDto = OrderMother.createOrderDto();


        when(orderFacade.createOrder(anyLong(), any(ShipmentMethod.class))).thenReturn(orderDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/orders/2/UPS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(201));
        matchResult(resultActions, "$", 1, false, false, 2);
    }

    @Test
    void testSetOrderToPaid() throws Exception {
        //Given
        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .orderDate(LocalDate.of(2022, 4, 26))
                .totalOrderPrice(new BigDecimal(100))
                .paid(true)
                .sent(false)
                .shipmentCompanyName("InPost")
                .shippingPrice(new BigDecimal(20))
                .deliveryDays(3)
                .address("Wilcza, 5/6, Warsaw, 02-234")
                .userId(2L)
                .clothesIdList(new ArrayList<>())
                .build();

        when(orderFacade.setOrderToPaid(anyLong())).thenReturn(orderDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/paid/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
        matchResult(resultActions, "$", 1, true, false, 2);
    }

    @Test
    void testSetOrderToSent() throws Exception {
        //Given
        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .orderDate(LocalDate.of(2022, 4, 26))
                .totalOrderPrice(new BigDecimal(100))
                .paid(true)
                .sent(true)
                .shipmentCompanyName("InPost")
                .shippingPrice(new BigDecimal(20))
                .deliveryDays(3)
                .address("Wilcza, 5/6, Warsaw, 02-234")
                .userId(2L)
                .clothesIdList(new ArrayList<>())
                .build();

        when(orderFacade.setOrderToSent(anyLong())).thenReturn(orderDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/sent/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
        matchResult(resultActions, "$", 1, true, true, 2);
    }

    @Test
    void testGetNotExistingOrder() throws Exception {
        //Given
        when(orderFacade.getOrder(anyLong())).thenThrow(new OrderNotFoundException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Order with given id doesn't exist or can't be found.")));
    }

    @Test
    void testSetPaidOrderToPaid() throws Exception {
        //Given
        when(orderFacade.setOrderToPaid(anyLong())).thenThrow(new OrderAlreadyPaidException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/paid/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(405))
                .andExpect(jsonPath("$", is("Order has already been paid.")));
    }

    @Test
    void testSetUnpaidOrderToSent() throws Exception {
        //Given
        when(orderFacade.setOrderToSent(anyLong())).thenThrow(new OrderNotPaidException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/sent/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(405))
                .andExpect(jsonPath("$", is("Order has not been paid yet.")));
    }

    @Test
    void testSetSentOrderToSent() throws Exception {
        //Given
        when(orderFacade.setOrderToSent(anyLong())).thenThrow(new OrderAlreadySentException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/sent/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(405))
                .andExpect(jsonPath("$", is("Order has already been sent.")));
    }

    @Test
    void testCreateOrderFromEmptyCart() throws Exception {
        //Given
        when(orderFacade.createOrder(anyLong(), any(ShipmentMethod.class))).thenThrow(new EmptyCartException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/orders/2/UPS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(405))
                .andExpect(jsonPath("$", is("You cannot create an order from an empty cart.")));
    }

    private void matchResult(ResultActions resultActions, String expression, int id, boolean paid, boolean sent, int userId) throws Exception {
        resultActions
                .andExpect(jsonPath(expression + ".id", Matchers.is(id)))
                .andExpect(jsonPath(expression + ".orderDate", Matchers.is("2022-04-26")))
                .andExpect(jsonPath(expression + ".totalOrderPrice", Matchers.is(100)))
                .andExpect(jsonPath(expression + ".paid", Matchers.is(paid)))
                .andExpect(jsonPath(expression + ".sent", Matchers.is(sent)))
                .andExpect(jsonPath(expression + ".shipmentCompanyName", Matchers.is("InPost")))
                .andExpect(jsonPath(expression + ".shippingPrice", Matchers.is(20)))
                .andExpect(jsonPath(expression + ".deliveryDays", Matchers.is(3)))
                .andExpect(jsonPath(expression + ".address", Matchers.is("Wilcza, 5/6, Warsaw, 02-234")))
                .andExpect(jsonPath(expression + ".userId", Matchers.is(userId)))
                .andExpect(jsonPath(expression + ".clothesIdList", Matchers.hasSize(0)));
    }
}