package com.clothes.factory.mapper;

import com.clothes.factory.domain.User;
import com.clothes.factory.domain.UserRequestDto;
import com.clothes.factory.domain.UserResponseDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    public User mapToUser(final UserRequestDto userRequestDto) throws CartNotFoundException, OrderNotFoundException {
        return User.builder()
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .emailAddress(userRequestDto.getEmailAddress())
                .password(userRequestDto.getPassword())
                .street(userRequestDto.getStreet())
                .streetAndApartmentNumber(userRequestDto.getStreetAndApartmentNumber())
                .city(userRequestDto.getCity())
                .postCode(userRequestDto.getPostCode())
                .cart(userRequestDto.getCartId() == null ? null : cartRepository.findById(userRequestDto.getCartId()).orElseThrow(CartNotFoundException::new))
                .ordersList(userRequestDto.getOrdersIdList() == null ? null : orderMapper.mapToOrdersFromIds(userRequestDto.getOrdersIdList()))
                .build();
    }

    public UserResponseDto mapToUserResponseDto(final User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .emailAddress(user.getEmailAddress())
                .street(user.getStreet())
                .streetAndApartmentNumber(user.getStreetAndApartmentNumber())
                .city(user.getCity())
                .postCode(user.getPostCode())
                .cartId(user.getCart().getId())
                .ordersIdList(orderMapper.mapToOrdersIdsFromOrders(user.getOrdersList()))
                .build();
    }

    public List<UserResponseDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserResponseDto)
                .toList();
    }

}
