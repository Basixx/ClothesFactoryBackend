package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.PaymentHistoryDto;
import com.kodilla.ClothesFactoryBackend.facade.PaymentHistoryFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(PaymentHistoryController.class)
public class PaymentHistoryControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentHistoryFacade paymentHistoryFacade;

    @Test
    void testGetAllPaymentHistory() throws Exception {
        //Given
        List<PaymentHistoryDto> paymentHistoryDtoList = new ArrayList<>();

        PaymentHistoryDto paymentHistoryDto1 = PaymentHistoryDto.builder()
                .id(1L)
                .paymentTime(LocalDateTime.of(2022, 5, 15, 12, 0))
                .orderId(2L)
                .userMail("test@mail.com")
                .price(new BigDecimal(100))
                .build();
        PaymentHistoryDto paymentHistoryDto2 = PaymentHistoryDto.builder()
                .id(3L)
                .paymentTime(LocalDateTime.of(2022, 6, 12, 9, 10))
                .orderId(4L)
                .userMail("test123@mail.com")
                .price(new BigDecimal(150))
                .build();

        paymentHistoryDtoList.add(paymentHistoryDto1);
        paymentHistoryDtoList.add(paymentHistoryDto2);

        when(paymentHistoryFacade.getAllPaymentHistory()).thenReturn(paymentHistoryDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/paymentHistory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath( "$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].paymentTime", is("2022-05-15T12:00:00")))
                .andExpect(jsonPath("$[0].orderId", is(2)))
                .andExpect(jsonPath("$[0].userMail", is("test@mail.com")))
                .andExpect(jsonPath("$[0].price", is(100)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].paymentTime", is("2022-06-12T09:10:00")))
                .andExpect(jsonPath("$[1].orderId", is(4)))
                .andExpect(jsonPath("$[1].userMail", is("test123@mail.com")))
                .andExpect(jsonPath("$[1].price", is(150)));
    }
}
