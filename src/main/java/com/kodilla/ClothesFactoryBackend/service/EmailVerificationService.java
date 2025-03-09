package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.email_verification_api.EmailVerificationApiClient;
import com.kodilla.ClothesFactoryBackend.domain.EmailVerificationDto;
import com.kodilla.ClothesFactoryBackend.exception.email.EmailVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationApiClient emailVerificationApiClient;

    public boolean emailExists(String email) throws EmailVerificationFailedException {
        EmailVerificationDto emailVerificationDto = emailVerificationApiClient.checkEmail(email);
        return emailVerificationDto.isEmailExists();
    }

}
