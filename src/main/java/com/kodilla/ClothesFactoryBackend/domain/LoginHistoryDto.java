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
public class LoginHistoryDto {

    private Long id;
    private LocalDateTime loginTime;
    private String userMail;
    private boolean succeed;

}
