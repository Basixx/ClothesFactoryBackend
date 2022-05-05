package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.AdminToken;
import com.kodilla.ClothesFactoryBackend.domain.AdminTokenDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminTokenMapper {
    public AdminToken mapToAdminToken(final AdminTokenDto adminTokenDto) {
        return AdminToken.builder()
                .token(adminTokenDto.getToken())
                .build();
    }

    public AdminTokenDto mapToAdminTokenDto(final AdminToken adminToken) {
        return AdminTokenDto.builder()
                .id(adminToken.getId())
                .token(adminToken.getToken())
                .build();
    }
}
