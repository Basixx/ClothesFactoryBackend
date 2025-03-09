package com.clothes.factory.facade;

import com.clothes.factory.domain.UserDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserEmailNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongPasswordException;
import com.clothes.factory.mapper.UserMapper;
import com.clothes.factory.service.UserService;
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
