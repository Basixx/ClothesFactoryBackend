package com.clothes.factory.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${admin.mail:defaultValue}")
    private String adminMail;

    @Value("${spring.mail.username:defaultValue}")
    private String shopEmail;

}
