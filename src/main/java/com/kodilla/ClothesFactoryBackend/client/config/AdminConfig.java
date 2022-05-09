package com.kodilla.ClothesFactoryBackend.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${admin.mail:defaultValue}")
    private String adminMail;
}
