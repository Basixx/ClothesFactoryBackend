package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothPrintContainsBadWordsException;
import com.kodilla.ClothesFactoryBackend.facade.ClothFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(ClothController.class)
class ClothControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClothFacade clothFacade;

    @Test
    public void testGetClothes() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

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
    public void testGetClothesFromUserCart() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

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
    public void testGetClothesFromOrder() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

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
    public void testCreateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

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
    public void testUpdateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

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
    public void testCreateClothProfanityFailed() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();
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

    private ClothDto createClothDto() {
        return ClothDto.builder()
                .id(1L)
                .fashion(Fashion.LONGSLEEVE)
                .print("MyPrint")
                .color(Color.RED)
                .printColor(Color.WHITE)
                .font(Font.ARIAL)
                .size(Size.M)
                .quantity(1)
                .price(new BigDecimal(50))
                .build();
    }

    private List<ClothDto> createClothDtoList() {
        List<ClothDto> clothesDto = new ArrayList<>();

        for (Long i = 0L; i < 3; i++) {
            clothesDto.add(ClothDto.builder()
                    .id(i + 1)
                    .fashion(Fashion.LONGSLEEVE)
                    .print("MyPrint " + i)
                    .color(Color.RED)
                    .printColor(Color.WHITE)
                    .font(Font.ARIAL)
                    .size(Size.M)
                    .quantity(1)
                    .price(new BigDecimal(50))
                    .build());
        }
        return clothesDto;
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
                .andExpect(jsonPath(expression + ".price", is(50)));
    }

    private void matchListResult(ResultActions resultActions) throws Exception {
        matchResult(resultActions, 1, "MyPrint 0", "$[0]");
        matchResult(resultActions, 2, "MyPrint 1", "$[1]");
        matchResult(resultActions, 3, "MyPrint 2", "$[2]");
    }
}