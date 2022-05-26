package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.api.QuoteNotFoundException;
import com.kodilla.ClothesFactoryBackend.service.KanyeQuoteService;
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
    private KanyeQuoteService kanyeQuoteService;

    @Test
    void testGetQuote() throws Exception {
        //Given
        KanyeQuoteDto kanyeQuoteDto = KanyeQuoteDto.builder().quote("quote").build();

        when(kanyeQuoteService.getQuote()).thenReturn(kanyeQuoteDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/kanye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.quote", is("quote")));
    }

    @Test
    void testQuoteNotFount() throws Exception {
        when(kanyeQuoteService.getQuote()).thenThrow(new QuoteNotFoundException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/kanye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Quote could not be found, please try again later.")));
    }
}