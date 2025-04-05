package com.clothes.factory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String street;
    private String streetAndApartmentNumber;
    private String city;
    private String postCode;
    private Long cartId;
    private List<Long> ordersIdList;

}
