package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.CartFacade;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CartController.class)
class CartControllerTestSuit {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartFacade cartFacade;

    @Test
    void getUserCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();

        when(cartFacade.getUserCart(anyLong())).thenReturn(cartDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/carts/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void addClothToCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();

        when(cartFacade.addClothToCart(anyLong(), anyLong())).thenReturn(cartDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(cartDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/addCloth/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteClothFromCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();

        when(cartFacade.addClothToCart(anyLong(), anyLong())).thenReturn(cartDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(cartDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    private CartDto createCartDto() {
        return CartDto.builder()
                .id(1L)
                .totalPrice(new BigDecimal(100))
                .clothesIdList(new ArrayList<>())
                .build();
    }
}