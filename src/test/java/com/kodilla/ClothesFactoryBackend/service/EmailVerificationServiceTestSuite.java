package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.email_verification_api.EmailVerificationApiClient;
import com.kodilla.ClothesFactoryBackend.domain.EmailVerificationDto;
import com.kodilla.ClothesFactoryBackend.exception.EmailVerificationFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailVerificationServiceTestSuite {

    @InjectMocks
    EmailVerificationService emailVerificationService;

    @Mock
    EmailVerificationApiClient emailVerificationApiClient;

    @Test
    void testEmailExists() throws EmailVerificationFailedException {
        //Given
        String email = "test@mail.com";
        EmailVerificationDto emailVerificationDto = EmailVerificationDto.builder().emailExists(true).build();
        when(emailVerificationApiClient.checkEmail(anyString())).thenReturn(emailVerificationDto);

        //When
        boolean exists = emailVerificationService.emailExists(email);

        //Then
        assertTrue(exists);
    }
}
