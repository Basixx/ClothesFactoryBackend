package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import com.kodilla.ClothesFactoryBackend.exception.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.facade.ExchangeRateFacade;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ExchangeRatesController.class)
public class ExchangeRatesControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateFacade exchangeRateFacade;

    @Test
    public void testGetExchangeRate() throws Exception {
        //Given
        ExchangeRateDto exchangeRateDto = ExchangeRateDto.builder()
                .id(1L)
                .fromCurrency("PLN")
                .toCurrency("EUR")
                .currencyRate(new BigDecimal(0.34))
                .build();

        when(exchangeRateFacade.getExchange(anyString(), anyString())).thenReturn(exchangeRateDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/exchange?from=PLN&to=USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fromCurrency", is("PLN")))
                .andExpect(jsonPath("$.toCurrency", is("EUR")))
                .andExpect(jsonPath("$.currencyRate", is(new BigDecimal(0.34))));
    }

    @Test
    public void testFailedExchangeRate() throws Exception {
        //Given
        when(exchangeRateFacade.getExchange(anyString(), anyString())).thenThrow(new CurrencyExchangeFailedException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/exchange?from=PLN&to=USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$", is("Currency exchange failed, try again later.")));
    }
}
