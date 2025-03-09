package com.clothes.factory.mapper;

import com.clothes.factory.domain.AdminToken;
import com.clothes.factory.domain.AdminTokenDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminTokenMapper {

    public AdminTokenDto mapToAdminTokenDto(final AdminToken adminToken) {
        return AdminTokenDto.builder()
                .id(adminToken.getId())
                .token(adminToken.getToken())
                .build();
    }

}
