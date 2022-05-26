package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailAddressDoesNotExistException;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailVerificationFailedException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserAlreadyExistsException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserEmailNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.WrongPasswordException;
import com.kodilla.ClothesFactoryBackend.mapper.UserMapper;
import com.kodilla.ClothesFactoryBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userService.getAllUsers());
    }

    public UserDto getUser(Long id) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUser(id));
    }

    public UserDto createUser(UserDto userDto) throws CartNotFoundException, OrderNotFoundException, UserAlreadyExistsException, EmailVerificationFailedException, EmailAddressDoesNotExistException {
        return userMapper.mapToUserDto(userService.createUser(userMapper.mapToUser(userDto)));
    }

    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, CartNotFoundException, OrderNotFoundException {
        return userMapper.mapToUserDto(userService.editUser(id, userMapper.mapToUser(userDto)));
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    public UserDto authenticateUser(String email, String password) throws UserEmailNotFoundException, WrongPasswordException {
        return userMapper.mapToUserDto(userService.authenticateUser(email, password));
    }
}