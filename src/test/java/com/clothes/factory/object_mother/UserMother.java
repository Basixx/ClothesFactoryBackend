package com.clothes.factory.object_mother;

import com.clothes.factory.domain.User;
import com.clothes.factory.domain.UserRequestDto;
import com.clothes.factory.domain.UserResponseDto;

import java.util.ArrayList;

public class UserMother {

    public static User createUser1() {
        return User.builder()
                .id(6L)
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password1")
                .street("Marszalkowska")
                .streetAndApartmentNumber("1/2")
                .city("Warsaw")
                .postCode("00-111")
                .ordersList(new ArrayList<>())
                .build();
    }

    public static User createUser2() {
        return User.builder()
                .id(7L)
                .name("Mike")
                .surname("Wazowski")
                .phoneNumber("222222222")
                .emailAddress("mike@wazowski.com")
                .password("password2")
                .street("Polinezyjska")
                .streetAndApartmentNumber("4/10")
                .city("Cracow")
                .postCode("00-222")
                .ordersList(new ArrayList<>())
                .build();
    }

    public static UserRequestDto createUserRequestDto() {
        return UserRequestDto.builder()
                .name("John")
                .surname("Smith")
                .phoneNumber("111222333")
                .emailAddress("john@smith.com")
                .password("password")
                .street("Marszalkowska")
                .streetAndApartmentNumber("1/2")
                .city("Warsaw")
                .postCode("00-111")
                .build();
    }

    public static UserResponseDto createUserResponseDto() {
        return UserResponseDto.builder()
                .id(1L)
                .name("John")
                .surname("Smith")
                .phoneNumber("111222333")
                .emailAddress("john@smith.com")
                .street("Marszalkowska")
                .streetAndApartmentNumber("1/2")
                .city("Warsaw")
                .postCode("00-111")
                .ordersIdList(new ArrayList<>())
                .cartId(2L)
                .build();
    }

}
