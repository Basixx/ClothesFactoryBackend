package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
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