package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.facade.ClothFacade;
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
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

        when(clothFacade.getClothes()).thenReturn(clothesDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void testGetClothesFromUserCart() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

        when(clothFacade.getClothesFromUserCart(anyLong())).thenReturn(clothesDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes/fromUserCart/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void testGetClothesFromOrder() throws Exception {
        // Given
        List<ClothDto> clothesDto = createClothDtoList();

        when(clothFacade.getClothesFromOrder(anyLong())).thenReturn(clothesDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/clothes/fromOrder/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void testCreateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

        when(clothFacade.createCloth(any(ClothDto.class))).thenReturn(clothDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/clothes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testUpdateCloth() throws Exception {
        //Given
        ClothDto clothDto = createClothDto();

        when(clothFacade.updateCloth(anyLong(), any(ClothDto.class))).thenReturn(clothDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(clothDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/clothes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
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
                .price(new BigDecimal(50))
                .build();
    }

    private List<ClothDto> createClothDtoList() {
        List<ClothDto> clothesDto = new ArrayList<>();

        for (Long i = 0L; i < 3; i++) {
            clothesDto.add(ClothDto.builder()
                    .id(i + 1)
                    .fashion(Fashion.LONGSLEEVE)
                    .print("MyPrint " + i + 1L)
                    .color(Color.RED)
                    .printColor(Color.WHITE)
                    .font(Font.ARIAL)
                    .size(Size.M)
                    .price(new BigDecimal(50))
                    .build());
        }
        return clothesDto;
    }
}