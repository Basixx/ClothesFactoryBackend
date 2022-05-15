package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.AdminTokenDto;
import com.kodilla.ClothesFactoryBackend.facade.AdminTokenFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(AdminTokenController.class)
public class AdminTokenControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testCreateAdminToken() throws Exception {
        //Given
        AdminTokenDto adminTokenDto = createAdminTokenDto();
        when(adminTokenFacade.createToken()).thenReturn(adminTokenDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void testDeleteTokens() throws Exception {
        //Given
        doNothing().when(adminTokenFacade).deleteAllTokens();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    private AdminTokenDto createAdminTokenDto() {
        return AdminTokenDto.builder().id(5L).token("ABCDEFG").build();
    }
}