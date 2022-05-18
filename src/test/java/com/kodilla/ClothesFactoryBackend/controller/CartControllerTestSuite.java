package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.CartFacade;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(CartController.class)
class CartControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartFacade cartFacade;

    @Test
    void getUserCart() throws Exception {
        //Given
        CartDto cartDto = CartMother.createCartDto();

        when(cartFacade.getUserCart(anyLong())).thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/carts/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
        matchResult(resultActions, 0);
    }

    @Test
    void addClothToCart() throws Exception {
        //Given
        CartDto cartDto = CartMother.createCartDto();
        cartDto.getClothesIdList().add(376L);

        when(cartFacade.addClothToCart(anyLong(), anyLong())).thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/addCloth/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.clothesIdList[0]", is(376)));
        matchResult(resultActions, 1);
    }

    @Test
    void deleteClothFromCart() throws Exception {
        //Given
        CartDto cartDto = CartMother.createCartDto();

        when(cartFacade.deleteClothFromCart(anyLong(), anyLong())).thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
        matchResult(resultActions, 0);
    }

    @Test
    void testAddNonExistingClothToCart() throws Exception {
        //Given
        CartDto cartDto = CartMother.createCartDto();
        cartDto.getClothesIdList().add(376L);

        when(cartFacade.addClothToCart(anyLong(), anyLong())).thenThrow(new ClothNotFoundException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/addCloth/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Cloth with given id doesn't exist or can't be found.")));
    }

    @Test
    void testAddClothToNonExistingCart() throws Exception {
        //Given
        when(cartFacade.addClothToCart(anyLong(), anyLong())).thenThrow(new CartNotFoundException());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/carts/addCloth/1/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Cart with given id doesn't exist or can't be found.")));
    }

    private void matchResult(ResultActions resultActions, int size) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(100)))
                .andExpect(jsonPath("$.clothesIdList", hasSize(size)));
    }
}