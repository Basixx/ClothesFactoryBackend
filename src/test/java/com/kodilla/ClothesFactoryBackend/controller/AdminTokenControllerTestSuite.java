package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.AdminTokenDto;
import com.kodilla.ClothesFactoryBackend.facade.AdminTokenFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig
@WebMvcTest(AdminTokenController.class)
public class AdminTokenControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminTokenFacade adminTokenFacade;

    @Test
    void testExistsToken() throws Exception {
        //Given
        when(adminTokenFacade.existsByToken("ABCDEFG")).thenReturn(true);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/token/ABCDEFG")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void testCreateAdminToken() throws Exception {
        //Given
        AdminTokenDto adminTokenDto = AdminTokenDto.builder().id(5L).token("ABCDEFG").build();
        when(adminTokenFacade.createToken()).thenReturn(adminTokenDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.token", is("ABCDEFG")));
    }

    @Test
    void testDeleteTokens() throws Exception {
        //Given
        doNothing().when(adminTokenFacade).deleteAllTokens();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }
}