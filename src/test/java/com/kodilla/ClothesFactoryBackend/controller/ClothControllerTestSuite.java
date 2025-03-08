package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothPrintContainsBadWordsException;
import com.kodilla.ClothesFactoryBackend.facade.ClothFacade;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ClothController.class)
class ClothControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ClothFacade clothFacade;

    @Test
    void testGetClothes() throws Exception {
        // Given
        List<ClothDto> clothesDto = ClothMother.createClothDtoList();

        when(clothFacade.getAllClothes()).thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
        matchListResult(resultActions);
    }

    @Test
    void testGetClothesFromUserCart() throws Exception {
        // Given
        List<ClothDto> clothesDto = ClothMother.createClothDtoList();

        when(clothFacade.getClothesFromUserCart(anyLong())).thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes/fromUserCart/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)));
        matchListResult(resultActions);
    }

    @Test
    void testGetClothesFromOrder() throws Exception {
        // Given
        List<ClothDto> clothesDto = ClothMother.createClothDtoList();

        when(clothFacade.getClothesFromOrder(anyLong())).thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes/fromOrder/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)));
        matchListResult(resultActions);
    }

    @Test
    void testCreateCloth() throws Exception {
        //Given
        ClothDto clothDto = ClothMother.createClothDto();

        when(clothFacade.createCloth(any(ClothDto.class))).thenReturn(clothDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/clothes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200));
        matchResult(resultActions, 1, "MyPrint", "$");
    }

    @Test
    void testUpdateCloth() throws Exception {
        //Given
        ClothDto clothDto = ClothMother.createClothDto();

        when(clothFacade.updateCloth(anyLong(), any(ClothDto.class))).thenReturn(clothDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/clothes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(200));
        matchResult(resultActions, 1, "MyPrint", "$");
    }

    @Test
    void testCreateClothProfanityFailed() throws Exception {
        //Given
        ClothDto clothDto = ClothMother.createClothDto();
        when(clothFacade.createCloth(any(ClothDto.class))).thenThrow(new ClothPrintContainsBadWordsException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/clothes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().is(405))
                .andExpect(jsonPath("$", is("Cloth print cannot contain any bad words, please change your choice.")));
    }

    private void matchResult(ResultActions resultActions, int id, String print, String expression) throws Exception {
        resultActions
                .andExpect(jsonPath(expression + ".id", is(id)))
                .andExpect(jsonPath(expression + ".fashion", is("LONGSLEEVE")))
                .andExpect(jsonPath(expression + ".print", is(print)))
                .andExpect(jsonPath(expression + ".color", is("RED")))
                .andExpect(jsonPath(expression + ".printColor", is("WHITE")))
                .andExpect(jsonPath(expression + ".size", is("M")))
                .andExpect(jsonPath(expression + ".quantity", is(1)))
                .andExpect(jsonPath(expression + ".price", is(70)));
    }

    private void matchListResult(ResultActions resultActions) throws Exception {
        matchResult(resultActions, 1, "MyPrint 0", "$[0]");
        matchResult(resultActions, 2, "MyPrint 1", "$[1]");
        matchResult(resultActions, 3, "MyPrint 2", "$[2]");
    }
}