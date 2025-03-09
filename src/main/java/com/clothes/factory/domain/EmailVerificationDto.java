package com.clothes.factory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmailVerificationDto {

    @JsonProperty("smtpCheck")
    private boolean emailExists;

}
