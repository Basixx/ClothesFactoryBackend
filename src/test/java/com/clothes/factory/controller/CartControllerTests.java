package com.clothes.factory.controller;

import com.clothes.factory.domain.CartDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.facade.CartFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.clothes.factory.object_mother.CartMother.createCartDto;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(CartController.class)
class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartFacade cartFacade;

    @Test
    void getUserCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();

        when(cartFacade.getUserCart(anyLong()))
                .thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/v1/carts/3")
                        .contentType(APPLICATION_JSON)
        ).andExpect(status().is(200));
        matchResult(resultActions, 0);
    }

    @Test
    void addClothToCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();
        cartDto.getClothesIdList().add(376L);

        when(cartFacade.addClothToCart(anyLong(), anyLong()))
                .thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                        put("/v1/carts/addCloth/1/3")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andExpect(status().is(200))
                .andExpect(jsonPath("$.clothesIdList[0]", is(376)));
        matchResult(resultActions, 1);
    }

    @Test
    void deleteClothFromCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();

        when(cartFacade.deleteClothFromCart(anyLong(), anyLong()))
                .thenReturn(cartDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                put("/v1/carts/1/3")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        ).andExpect(status().is(200));
        matchResult(resultActions, 0);
    }

    @Test
    void testAddNonExistingClothToCart() throws Exception {
        //Given
        CartDto cartDto = createCartDto();
        cartDto.getClothesIdList().add(376L);

        when(cartFacade.addClothToCart(anyLong(), anyLong()))
                .thenThrow(new ClothNotFoundException());

        //When & Then
        mockMvc.perform(
                        put("/v1/carts/addCloth/1/3")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Cloth with given id doesn't exist or can't be found.")));
    }

    @Test
    void testAddClothToNonExistingCart() throws Exception {
        //Given
        when(cartFacade.addClothToCart(anyLong(), anyLong()))
                .thenThrow(new CartNotFoundException());

        //When & Then
        mockMvc.perform(
                        put("/v1/carts/addCloth/1/3")
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andExpect(status().is(404))
                .andExpect(jsonPath("$", is("Cart with given id doesn't exist or can't be found.")));
    }

    private void matchResult(ResultActions resultActions, int size) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(100)))
                .andExpect(jsonPath("$.clothesIdList", hasSize(size)));
    }

}
