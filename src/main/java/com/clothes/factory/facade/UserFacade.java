package com.clothes.factory.facade;

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

    public List<UserResponseDto> getUsers(int page, int size) {
        return userMapper.mapToUserDtoList(userService.getAllUsers(page, size));
    }

    public UserResponseDto getUser(Long id) throws UserNotFoundException {
        return userMapper.mapToUserResponseDto(userService.getUser(id));
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) throws CartNotFoundException, OrderNotFoundException, UserAlreadyExistsException, EmailVerificationFailedException, EmailAddressDoesNotExistException {
        return userMapper.mapToUserResponseDto(userService.createUser(userMapper.mapToUser(userRequestDto)));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) throws UserNotFoundException, CartNotFoundException, OrderNotFoundException {
        return userMapper.mapToUserResponseDto(userService.editUser(id, userMapper.mapToUser(userRequestDto)));
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    public UserResponseDto authenticateUser(String email, String password) throws UserEmailNotFoundException, WrongPasswordException {
        return userMapper.mapToUserResponseDto(userService.authenticateUser(email, password));
    }

}
