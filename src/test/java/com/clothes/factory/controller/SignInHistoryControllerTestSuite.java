package com.clothes.factory.controller;

import com.clothes.factory.domain.SignInHistoryDto;
import com.clothes.factory.facade.SignInHistoryFacade;
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
@WebMvcTest(SignInHistoryController.class)
public class SignInHistoryControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SignInHistoryFacade signInHistoryFacade;

    @Test
    void testGetSignInHistory() throws Exception {
        //Given
        List<SignInHistoryDto> signInHistoryDtoList = new ArrayList<>();

        SignInHistoryDto signInHistoryDto1 = SignInHistoryDto.builder()
                .id(1L)
                .signInTime(LocalDateTime.of(2022, 5, 30, 12, 30, 0))
                .userMail("test1@mail.com")
                .userNumber("123456789")
                .build();
        SignInHistoryDto signInHistoryDto2 = SignInHistoryDto.builder()
                .id(2L)
                .signInTime(LocalDateTime.of(2022, 6, 1, 11, 45, 0))
                .userMail("test2@mail.com")
                .userNumber("987654321")
                .build();

        signInHistoryDtoList.add(signInHistoryDto1);
        signInHistoryDtoList.add(signInHistoryDto2);

        when(signInHistoryFacade.getAllSignInHistory()).thenReturn(signInHistoryDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/signInHistory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].signInTime", is("2022-05-30T12:30:00")))
                .andExpect(jsonPath("$[0].userMail", is("test1@mail.com")))
                .andExpect(jsonPath("$[0].userNumber", is("123456789")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].signInTime", is("2022-06-01T11:45:00")))
                .andExpect(jsonPath("$[1].userMail", is("test2@mail.com")))
                .andExpect(jsonPath("$[1].userNumber", is("987654321")));
    }
}