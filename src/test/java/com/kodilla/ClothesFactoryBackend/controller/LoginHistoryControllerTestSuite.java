package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.LoginHistoryDto;
import com.kodilla.ClothesFactoryBackend.facade.LoginHistoryFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(LoginHistoryController.class)
public class LoginHistoryControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private LoginHistoryFacade loginHistoryFacade;

    @Test
    void testGetLoginHistory() throws Exception {
        //Given
        List<LoginHistoryDto> loginHistoryDtoList = new ArrayList<>();

        LoginHistoryDto loginHistoryDto1 = LoginHistoryDto.builder()
                .id(1L)
                .loginTime(LocalDateTime.of(2022, 4, 25, 15, 0, 0))
                .userMail("test@mail.com")
                .succeed(true)
                .build();
        LoginHistoryDto loginHistoryDto2 = LoginHistoryDto.builder()
                .id(2L)
                .loginTime(LocalDateTime.of(2022, 3, 12, 12, 30))
                .userMail("test123@mail.com")
                .succeed(false)
                .build();

        loginHistoryDtoList.add(loginHistoryDto1);
        loginHistoryDtoList.add(loginHistoryDto2);

        when(loginHistoryFacade.getAllLoginHistory()).thenReturn(loginHistoryDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/loginHistory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].loginTime", is("2022-04-25T15:00:00")))
                .andExpect(jsonPath("$[0].userMail", is("test@mail.com")))
                .andExpect(jsonPath("$[0].succeed", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].loginTime", is("2022-03-12T12:30:00")))
                .andExpect(jsonPath("$[1].userMail", is("test123@mail.com")))
                .andExpect(jsonPath("$[1].succeed", is(false)));
    }
}
