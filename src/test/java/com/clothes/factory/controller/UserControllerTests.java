package com.clothes.factory.controller;

import com.clothes.factory.domain.UserRequestDto;
import com.clothes.factory.domain.UserResponseDto;
import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongCredentialsException;
import com.clothes.factory.facade.UserFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.clothes.factory.object_mother.UserMother.createUserRequestDto;
import static com.clothes.factory.object_mother.UserMother.createUserResponseDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserFacade userFacade;

    @Test
    void testGetUsers() throws Exception {
        //Given
        List<UserResponseDto> usersDto = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            usersDto.add(UserResponseDto.builder()
                    .id(i + 1L)
                    .name("John")
                    .surname("Smith")
                    .phoneNumber("111222333")
                    .emailAddress("john@smith.com")
                    .ordersIdList(new ArrayList<>())
                    .cartId(2L)
                    .build());
        }

        when(userFacade.getUsers(anyInt(), anyInt()))
                .thenReturn(usersDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                        get("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
        matchResult(
                resultActions,
                "$[0]",
                1
        );
        matchResult(
                resultActions,
                "$[1]",
                2
        );
        matchResult(
                resultActions,
                "$[2]",
                3
        );
    }

    @Test
    void testGetUser() throws Exception {
        //Given
        UserResponseDto userResponseDto = createUserResponseDto();

        when(userFacade.getUser(anyLong())).thenReturn(userResponseDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testCreateUser() throws Exception {
        //Given
        UserResponseDto userResponseDto = createUserResponseDto();

        when(userFacade.createUser(any(UserRequestDto.class)))
                .thenReturn(userResponseDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userResponseDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
        ).andExpect(MockMvcResultMatchers.status().is(201));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testUpdateUser() throws Exception {
        //Given
        UserResponseDto userResponseDto = createUserResponseDto();

        when(userFacade.updateUser(anyLong(), any(UserRequestDto.class)))
                .thenReturn(userResponseDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userResponseDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
        ).andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testDeleteUser() throws Exception {
        //Given
        doNothing().when(userFacade)
                .deleteUser(anyLong());

        //When & Then
        mockMvc.perform(
                delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        ).andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void testAuthenticateUser() throws Exception {
        //Given
        UserResponseDto userResponseDto = createUserResponseDto();
        when(userFacade.authenticateUser(anyString(), anyString()))
                .thenReturn(userResponseDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/users/mail@test.com/password")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testGetNonExistingUser() throws Exception {
        //Given
        when(userFacade.getUser(anyLong()))
                .thenThrow(new UserNotFoundException());

        //When & Then
        mockMvc.perform(
                        get("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(jsonPath("$", Matchers.is("User with given id doesn't exist or can't be found.")));
    }

    @Test
    void testAuthenticateNonExistingUser() throws Exception {
        //Given
        when(userFacade.authenticateUser(anyString(), anyString()))
                .thenThrow(new WrongCredentialsException());

        //When & Then
        mockMvc.perform(
                        get("/users/mail@test.com/password")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(jsonPath("$", Matchers.is("Please check your credentials.")));
    }

    @Test
    void testCreateUserWithAlreadyUsedEmail() throws Exception {
        //Given
        UserRequestDto userRequestDto = createUserRequestDto();

        when(userFacade.createUser(any(UserRequestDto.class)))
                .thenThrow(new UserAlreadyExistsException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userRequestDto);

        //When & Then
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(405))
                .andExpect(jsonPath("$", Matchers.is("Given email is already setup for an account.")));
    }

    @Test
    void testCreateUserWithNonExistingEmail() throws Exception {
        //Given
        UserRequestDto userRequestDto = createUserRequestDto();

        when(userFacade.createUser(any(UserRequestDto.class)))
                .thenThrow(new EmailAddressDoesNotExistException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userRequestDto);

        //When & Then
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$", Matchers.is("This email address does not exist, please provide a different one.")));
    }

    @Test
    void testCreateUserEmailVerificationFailed() throws Exception {
        //Given
        UserRequestDto userRequestDto = createUserRequestDto();

        when(userFacade.createUser(any(UserRequestDto.class))).thenThrow(new EmailVerificationFailedException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userRequestDto);

        //When & Then
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$", Matchers.is("Email verification failed, please try again later.")));
    }

    private void matchResult(
            ResultActions resultActions,
            String expr,
            int id
    ) throws Exception {
        resultActions
                .andExpect(jsonPath("%s.id".formatted(expr), Matchers.is(id)))
                .andExpect(jsonPath("%s.name".formatted(expr), Matchers.is("John")))
                .andExpect(jsonPath("%s.surname".formatted(expr), Matchers.is("Smith")))
                .andExpect(jsonPath("%s.phoneNumber".formatted(expr), Matchers.is("111222333")))
                .andExpect(jsonPath("%s.emailAddress".formatted(expr), Matchers.is("john@smith.com")))
                .andExpect(jsonPath("%s.ordersIdList".formatted(expr), Matchers.hasSize(0)))
                .andExpect(jsonPath("%s.cartId".formatted(expr), Matchers.is(2)));
    }

}
