package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminTokenDto {

    private Long id;
    private String token;
}
