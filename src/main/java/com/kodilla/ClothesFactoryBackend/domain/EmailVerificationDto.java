package com.kodilla.ClothesFactoryBackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class EmailVerificationDto {

    @JsonProperty("smtpCheck")
    private boolean emailExists;
}
