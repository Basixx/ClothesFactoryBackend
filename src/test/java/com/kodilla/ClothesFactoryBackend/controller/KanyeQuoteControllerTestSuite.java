package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.client.kanye_west_quotes_api.KanyeWestApiClient;
import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(KanyeQuoteController.class)
public class KanyeQuoteControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private KanyeWestApiClient kanyeWestApiClient;

    @Test
    void testGetQuote() throws Exception {
        //Given
        KanyeQuoteDto kanyeQuoteDto = KanyeQuoteDto.builder().quote("quote").build();

        when(kanyeWestApiClient.getQuote()).thenReturn(kanyeQuoteDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/kanye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", is("quote")));
    }

    @Test
    void testQuoteNotFount() throws Exception {
        when(kanyeWestApiClient.getQuote()).thenThrow(new QuoteNotFoundException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/kanye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Quote could not be found, please try again later.")));
    }
}