package com.clothes.factory.service;

import com.clothes.factory.client.email_verification_api.EmailVerificationApiClient;
import com.clothes.factory.domain.EmailVerificationDto;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
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
