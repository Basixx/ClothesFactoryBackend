package com.clothes.factory.mapper;

import com.clothes.factory.domain.AdminToken;
import com.clothes.factory.domain.AdminTokenDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminTokenMapperTestSuite {

    @InjectMocks
    private AdminTokenMapper adminTokenMapper;

    @Test
    void testMapToAdminTokenDto() {
        //Given
        AdminToken adminToken = AdminToken.builder()
                .token("123456ABCDEFG")
                .build();

        //When
        AdminTokenDto adminTokenDto = adminTokenMapper.mapToAdminTokenDto(adminToken);

        //Then
        assertEquals("123456ABCDEFG", adminTokenDto.getToken());
    }
}