package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.facade.OrderFacade;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(OrderController.class)
class OrderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderFacade orderFacade;

    @Test
    void testGetOrder() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();

        when(orderFacade.getOrder(anyLong())).thenReturn(orderDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllOrders() throws Exception {
        //Given
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Long i = 0L; i < 3; i++) {
            ordersDto.add(OrderDto.builder()
                    .id(i + 1L)
                    .orderDate(LocalDate.of(2022, 4, 26))
                    .totalOrderPrice(new BigDecimal(100))
                    .paid(false)
                    .sent(false)
                    .userId(i + 2L)
                    .clothesIdList(new ArrayList<>())
                    .build());

        }

        when(orderFacade.getAllOrders()).thenReturn(ordersDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));

    }

    @Test
    void testGetOrdersByUser() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();

        when(orderFacade.getOrder(anyLong())).thenReturn(orderDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/orders/byUser/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testCreateOrder() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();

        when(orderFacade.createOrder(anyLong())).thenReturn(orderDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/orders/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void testSetOrderToPaid() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();

        when(orderFacade.setOrderToPaid(anyLong())).thenReturn(orderDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/paid/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testSetOrderToSent() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();

        when(orderFacade.setOrderToPaid(anyLong())).thenReturn(orderDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/sent/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    private OrderDto createOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .orderDate(LocalDate.of(2022, 4, 26))
                .totalOrderPrice(new BigDecimal(100))
                .paid(false)
                .sent(false)
                .userId(2L)
                .clothesIdList(new ArrayList<>())
                .build();
    }
}