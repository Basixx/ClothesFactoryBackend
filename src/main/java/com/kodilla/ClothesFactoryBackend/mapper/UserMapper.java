package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapper {
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    public User mapToUser(final UserDto userDto) throws UserNotFoundException, OrderNotFoundException {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .phoneNumber(userDto.getPhoneNumber())
                .emailAddress(userDto.getEmailAddress())
                .password(userDto.getPassword())
                .cart(cartRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new))
                .ordersList(orderMapper.mapToOrdersFromIds(userDto.getOrdersIdList()))
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .cartId(user.getCart().getId())
                .ordersIdList(orderMapper.mapToOrdersIdsFromOrders(user.getOrdersList()))
                .build();
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}