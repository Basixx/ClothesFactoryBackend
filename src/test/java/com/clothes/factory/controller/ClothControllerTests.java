package com.clothes.factory.controller;

import com.clothes.factory.domain.ClothDto;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.facade.ClothFacade;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.clothes.factory.object_mother.ClothMother.createClothDto;
import static com.clothes.factory.object_mother.ClothMother.createClothDtoList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ClothController.class)
class ClothControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClothFacade clothFacade;

    @Test
    void testGetClothes() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

        when(clothFacade.getAllClothes(anyInt(), anyInt()))
                .thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/clothes")
                        .contentType(APPLICATION_JSON)
        ).andExpect(status().is(200));
        matchListResult(resultActions);
    }

    @Test
    void testGetClothesFromUserCart() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

        when(clothFacade.getClothesFromUserCart(anyLong()))
                .thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                        get("/users/3/cart/clothes")
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)));
        matchListResult(resultActions);
    }

    @Test
    void testGetClothesFromOrder() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

        when(clothFacade.getClothesFromOrder(anyLong()))
                .thenReturn(clothesDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                        get("/orders/3/clothes")
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(3)));
        matchListResult(resultActions);
    }

    @Test
    void testCreateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

        when(clothFacade.createCloth(any(ClothDto.class)))
                .thenReturn(clothDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                post("/clothes")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
        ).andExpect(status().is(201));
        matchResult(resultActions, 1, "MyPrint", "$");
    }

    @Test
    void testUpdateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

        when(clothFacade.updateCloth(anyLong(), any(ClothDto.class)))
                .thenReturn(clothDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                put("/clothes/1")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
        ).andExpect(status().is(200));
        matchResult(resultActions, 1, "MyPrint", "$");
    }

    @Test
    void testCreateClothProfanityFailed() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();
        when(clothFacade.createCloth(any(ClothDto.class)))
                .thenThrow(new ClothPrintContainsBadWordsException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        mockMvc.perform(
                        post("/clothes")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(status().is(405))
                .andExpect(jsonPath("$", is("Cloth print cannot contain any bad words, please change your choice.")));
    }

    private void matchResult(
            ResultActions resultActions,
            int id,
            String print,
            String expr
    ) throws Exception {
        resultActions
                .andExpect(jsonPath("%s.id".formatted(expr), is(id)))
                .andExpect(jsonPath("%s.fashion".formatted(expr), is("LONGSLEEVE")))
                .andExpect(jsonPath("%s.print".formatted(expr), is(print)))
                .andExpect(jsonPath("%s.color".formatted(expr), is("RED")))
                .andExpect(jsonPath("%s.printColor".formatted(expr), is("WHITE")))
                .andExpect(jsonPath("%s.size".formatted(expr), is("M")))
                .andExpect(jsonPath("%s.quantity".formatted(expr), is(1)))
                .andExpect(jsonPath("%s.price".formatted(expr), is(70)));
    }

    private void matchListResult(ResultActions resultActions) throws Exception {
        matchResult(
                resultActions,
                1,
                "MyPrint 0",
                "$[0]"
        );
        matchResult(
                resultActions,
                2,
                "MyPrint 1",
                "$[1]"
        );
        matchResult(
                resultActions,
                3,
                "MyPrint 2",
                "$[2]"
        );
    }

}
