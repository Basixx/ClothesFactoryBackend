package com.kodilla.ClothesFactoryBackend.configuration;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class TestConfiguration {

    @MockBean
    JavaMailSender javaMailSender;
}
