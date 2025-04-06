package com.clothes.factory.controller;

import com.clothes.factory.domain.UserRequestDto;
import com.clothes.factory.domain.UserResponseDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserEmailNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongPasswordException;
import com.clothes.factory.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    @ResponseStatus(OK)
    public List<UserResponseDto> getUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return userFacade.getUsers(page, size);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(OK)
    public UserResponseDto getUser(@PathVariable Long id) throws UserNotFoundException {
        return userFacade.getUser(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) throws CartNotFoundException, OrderNotFoundException, UserAlreadyExistsException, EmailVerificationFailedException, EmailAddressDoesNotExistException {
        return userFacade.createUser(userRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) throws UserNotFoundException, CartNotFoundException, OrderNotFoundException {
        return userFacade.updateUser(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userFacade.deleteUser(id);
    }

    @GetMapping(value = "/{email}/{password}")
    @ResponseStatus(OK)
    public UserResponseDto authenticateUser(@PathVariable String email, @PathVariable String password) throws UserEmailNotFoundException, WrongPasswordException {
        return userFacade.authenticateUser(email, password);
    }

}
