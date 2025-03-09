package com.clothes.factory.facade;

import com.clothes.factory.domain.AdminTokenDto;
import com.clothes.factory.mapper.AdminTokenMapper;
import com.clothes.factory.service.AdminTokenService;
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

    public AdminTokenDto createToken() {
        return adminTokenMapper.mapToAdminTokenDto(adminTokenService.saveToken());
    }

    public void deleteAllTokens() {
        adminTokenService.deleteAllTokens();
    }

}
