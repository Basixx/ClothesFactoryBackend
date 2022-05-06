package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.exception.UserEmailNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.UserFacade;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws CartNotFoundException, OrderNotFoundException, UserAlreadyExistsException {
        return ResponseEntity.ok(userFacade.createUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException, CartNotFoundException, OrderNotFoundException {
        return ResponseEntity.ok(userFacade.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{email}/{password}")
    public ResponseEntity<UserDto> authenticateUser(@PathVariable String email, @PathVariable String password) throws UserEmailNotFoundException, WrongPasswordException {
        return ResponseEntity.ok(userFacade.authenticateUser(email, password));
    }
}