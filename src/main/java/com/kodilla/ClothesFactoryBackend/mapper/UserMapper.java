package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.domain.UserDto;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    public User mapToUser(final UserDto userDto) throws CartNotFoundException, OrderNotFoundException {

        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .phoneNumber(userDto.getPhoneNumber())
                .emailAddress(userDto.getEmailAddress())
                .password(userDto.getPassword())
                .street(userDto.getStreet())
                .streetAndApartmentNumber(userDto.getStreetAndApartmentNumber())
                .city(userDto.getCity())
                .postCode(userDto.getPostCode())
                .cart(userDto.getCartId() == null ? null : cartRepository.findById(userDto.getCartId()).orElseThrow(CartNotFoundException::new))
                .ordersList(userDto.getOrdersIdList() == null ? null : orderMapper.mapToOrdersFromIds(userDto.getOrdersIdList()))
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
                .street(user.getStreet())
                .streetAndApartmentNumber(user.getStreetAndApartmentNumber())
                .city(user.getCity())
                .postCode(user.getPostCode())
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
