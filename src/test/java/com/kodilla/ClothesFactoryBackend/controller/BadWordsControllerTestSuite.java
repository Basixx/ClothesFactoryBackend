package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.exception.ProfanityCheckFailedException;
import com.kodilla.ClothesFactoryBackend.facade.BadWordsFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig
@WebMvcTest(BadWordsController.class)
public class BadWordsControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BadWordsFacade badWordsFacade;

    private String getJsonContent() {
        Gson gson = new Gson();
        return gson.toJson("Banana");
    }

    @Test
    void testGetProfanityCheck() throws Exception {
        //Given
        when(badWordsFacade.getProfanityCheck(anyString())).thenReturn(true);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/badWords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getJsonContent()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void testFailedProfanityCheck() throws Exception{
        //Given
        when(badWordsFacade.getProfanityCheck(anyString())).thenThrow(new ProfanityCheckFailedException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/badWords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getJsonContent()))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$", is("Profanity check failed, try again later.")));
    }
}
