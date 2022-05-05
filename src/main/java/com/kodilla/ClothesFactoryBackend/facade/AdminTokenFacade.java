package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.AdminTokenDto;
import com.kodilla.ClothesFactoryBackend.mapper.AdminTokenMapper;
import com.kodilla.ClothesFactoryBackend.service.AdminTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTokenFacade {
    private final AdminTokenMapper adminTokenMapper;
    private final AdminTokenService adminTokenService;

    public boolean existsByToken(String token) {
        return adminTokenService.existsByToken(token);
    }

    public AdminTokenDto createToken(AdminTokenDto adminTokenDto) {
        return adminTokenMapper.mapToAdminTokenDto(adminTokenService.saveToken(adminTokenMapper.mapToAdminToken(adminTokenDto)));
    }
}
