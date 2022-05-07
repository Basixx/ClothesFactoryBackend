package com.kodilla.ClothesFactoryBackend.controller;

import com.google.gson.Gson;
import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.facade.UserFacade;
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
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserFacade userFacade;

    @Test
    void testGetUsers() throws Exception {
        //Given
        List<UserDto> usersDto = new ArrayList<>();
        for (Long i = 0L; i < 3; i++) {
            usersDto.add(UserDto.builder()
                    .id(i + 1)
                    .name("John")
                    .surname("Smith")
                    .phoneNumber("111222333")
                    .emailAddress("john@smith.com")
                    .password("password")
                    .ordersIdList(new ArrayList<>())
                    .cartId(2L)
                    .build());
        }

        when(userFacade.getUsers()).thenReturn(usersDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    void testGetUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.getUser(anyLong())).thenReturn(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testCreateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.createUser(any(UserDto.class))).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void testUpdateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.updateUser(anyLong(), any(UserDto.class))).thenReturn(userDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testDeleteUser() throws Exception {
        //Given
        doNothing().when(userFacade).deleteUser(anyLong());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .id(1L)
                .name("John")
                .surname("Smith")
                .phoneNumber("111222333")
                .emailAddress("john@smith.com")
                .password("password")
                .ordersIdList(new ArrayList<>())
                .cartId(2L)
                .build();
    }
}