package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
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

    public UserDto createUser(UserDto userDto) throws UserNotFoundException, OrderNotFoundException {
        return userMapper.mapToUserDto(userService.createUser(userMapper.mapToUser(userDto)));
    }

    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, OrderNotFoundException {
        return userMapper.mapToUserDto(userService.editUser(id, userMapper.mapToUser(userDto)));
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}