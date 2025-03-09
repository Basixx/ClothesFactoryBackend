package com.kodilla.ClothesFactoryBackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInHistoryDto {

    private Long id;
    private LocalDateTime signInTime;
    private String userMail;
    private String userNumber;

}
