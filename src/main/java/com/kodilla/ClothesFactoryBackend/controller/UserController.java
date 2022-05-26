package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailAddressDoesNotExistException;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailVerificationFailedException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserAlreadyExistsException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserEmailNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.WrongPasswordException;
import com.kodilla.ClothesFactoryBackend.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userFacade.getUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userFacade.getUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws CartNotFoundException, OrderNotFoundException, UserAlreadyExistsException, EmailVerificationFailedException, EmailAddressDoesNotExistException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.createUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException, CartNotFoundException, OrderNotFoundException {
        return ResponseEntity.ok(userFacade.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userFacade.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{email}/{password}")
    public ResponseEntity<UserDto> authenticateUser(@PathVariable String email, @PathVariable String password) throws UserEmailNotFoundException, WrongPasswordException {
        return ResponseEntity.ok(userFacade.authenticateUser(email, password));
    }
}