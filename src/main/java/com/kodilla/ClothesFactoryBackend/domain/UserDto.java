package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private Long cartId;
    private List<Long> ordersIdList;
}