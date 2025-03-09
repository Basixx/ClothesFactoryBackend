package com.clothes.factory.controller;

import com.clothes.factory.domain.UserDto;
import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserEmailNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongPasswordException;
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

import static com.clothes.factory.object_mother.UserMother.createUserDto;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<UserDto> usersDto = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            usersDto.add(UserDto.builder()
                    .id(i + 1L)
                    .name("John")
                    .surname("Smith")
                    .phoneNumber("111222333")
                    .emailAddress("john@smith.com")
                    .password("password")
                    .ordersIdList(new ArrayList<>())
                    .cartId(2L)
                    .build());
        }

        when(userFacade.getUsers())
                .thenReturn(usersDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                        get("/v1/users")
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
        UserDto userDto = createUserDto();

        when(userFacade.getUser(anyLong())).thenReturn(userDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testCreateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.createUser(any(UserDto.class)))
                .thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
        ).andExpect(MockMvcResultMatchers.status().is(201));
        matchResult(resultActions, "$", 1);
    }

    @Test
    void testUpdateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.updateUser(anyLong(), any(UserDto.class)))
                .thenReturn(userDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                put("/v1/users/1")
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
                delete("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
        ).andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void testAuthenticateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();
        when(userFacade.authenticateUser(anyString(), anyString()))
                .thenReturn(userDto);

        //When & Then
        ResultActions resultActions = mockMvc.perform(
                get("/v1/users/mail@test.com/password")
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
                        get("/v1/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(jsonPath("$", Matchers.is("User with given id doesn't exist or can't be found.")));
    }

    @Test
    void testAuthenticateNonExistingUser() throws Exception {
        //Given
        when(userFacade.authenticateUser(anyString(), anyString()))
                .thenThrow(new UserEmailNotFoundException());

        //When & Then
        mockMvc.perform(
                        get("/v1/users/mail@test.com/password")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(jsonPath("$", Matchers.is("User with given email does not exist.")));
    }

    @Test
    void testAuthenticateUserWithWrongPassword() throws Exception {
        //Given
        when(userFacade.authenticateUser(anyString(), anyString()))
                .thenThrow(new WrongPasswordException());

        //When & Then
        mockMvc.perform(
                        get("/v1/users/mail@test.com/password")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
                ).andExpect(jsonPath("$", Matchers.is("Wrong password.")));
    }

    @Test
    void testCreateUserWithAlreadyUsedEmail() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.createUser(any(UserDto.class)))
                .thenThrow(new UserAlreadyExistsException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(
                        post("/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(405))
                .andExpect(jsonPath("$", Matchers.is("Given email is already setup for an account.")));
    }

    @Test
    void testCreateUserWithNonExistingEmail() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.createUser(any(UserDto.class)))
                .thenThrow(new EmailAddressDoesNotExistException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(
                        post("/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$", Matchers.is("This email address does not exist, please provide a different one.")));
    }

    @Test
    void testCreateUserEmailVerificationFailed() throws Exception {
        //Given
        UserDto userDto = createUserDto();

        when(userFacade.createUser(any(UserDto.class))).thenThrow(new EmailVerificationFailedException());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(
                        post("/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonContent)
                ).andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$", Matchers.is("Email verification failed, please try again later.")));
    }

    private void matchResult(ResultActions resultActions,
                             String expression,
                             int id) throws Exception {
        resultActions
                .andExpect(jsonPath(expression + ".id", Matchers.is(id)))
                .andExpect(jsonPath(expression + ".name", Matchers.is("John")))
                .andExpect(jsonPath(expression + ".surname", Matchers.is("Smith")))
                .andExpect(jsonPath(expression + ".phoneNumber", Matchers.is("111222333")))
                .andExpect(jsonPath(expression + ".emailAddress", Matchers.is("john@smith.com")))
                .andExpect(jsonPath(expression + ".password", Matchers.is("password")))
                .andExpect(jsonPath(expression + ".ordersIdList", Matchers.hasSize(0)))
                .andExpect(jsonPath(expression + ".cartId", Matchers.is(2)));
    }

}
