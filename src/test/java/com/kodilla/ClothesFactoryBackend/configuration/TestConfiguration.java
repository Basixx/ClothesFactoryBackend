package com.kodilla.ClothesFactoryBackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Configuration
public class TestConfiguration {

    @MockitoBean
    JavaMailSender javaMailSender;

}
