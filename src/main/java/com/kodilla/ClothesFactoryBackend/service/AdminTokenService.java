package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.AdminToken;
import com.kodilla.ClothesFactoryBackend.mail.AdminMailCreator;
import com.kodilla.ClothesFactoryBackend.repository.AdminTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminTokenService {
    private final AdminTokenRepository adminTokenRepository;
    private final EmailService emailService;
    private final AdminMailCreator adminMailCreator;

    public Boolean existsByToken(final String token) {
        return adminTokenRepository.existsByToken(token);
    }

    public AdminToken saveToken() {
        deleteAllTokens();
        String tokenString = UUID.randomUUID().toString();
        AdminToken adminToken = AdminToken.builder().token(tokenString).build();
        emailService.send(adminMailCreator.createTokenMail(tokenString));
        return adminTokenRepository.save(adminToken);
    }

    public void deleteAllTokens() {
        adminTokenRepository.deleteAll();
    }
}
