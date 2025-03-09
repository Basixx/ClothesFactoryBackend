package com.clothes.factory.service;

import com.clothes.factory.client.email_verification_api.EmailVerificationApiClient;
import com.clothes.factory.domain.EmailVerificationDto;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
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
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto();
        emailVerificationDto.setEmailExists(true);
        when(emailVerificationApiClient.checkEmail(anyString())).thenReturn(emailVerificationDto);

        //When
        boolean exists = emailVerificationService.emailExists(email);

        //Then
        assertTrue(exists);
    }
}
