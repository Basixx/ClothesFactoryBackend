package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.AdminToken;
import com.kodilla.ClothesFactoryBackend.mail.AdminMailCreator;
import com.kodilla.ClothesFactoryBackend.repository.AdminTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminTokenServiceTestSuite {

    @InjectMocks
    private AdminTokenService adminTokenService;

    @Mock
    private AdminTokenRepository adminTokenRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private AdminMailCreator adminMailCreator;

    @Test
    void testExistsByToken() {
        //Given
        when(adminTokenRepository.existsByToken("ABCDEFG")).thenReturn(true);
        when(adminTokenRepository.existsByToken("KLMNOP")).thenReturn(false);
        //When
        boolean exists1 = adminTokenService.existsByToken("ABCDEFG");
        boolean exists2 = adminTokenService.existsByToken("KLMNOP");
        //Then
        assertTrue(exists1);
        assertFalse(exists2);
    }

    @Test
    void testSaveToken() {
        //Given
        doNothing().when(adminTokenRepository).deleteAll();
        when(adminTokenRepository.save(any())).thenReturn(AdminToken.builder().id(5L).token("ABCDEF").build());

        //When
        AdminToken adminToken = adminTokenService.saveToken();

        //Then
        verify(emailService, times(1)).send(any());
        assertEquals(5L, adminToken.getId());
        assertEquals("ABCDEF", adminToken.getToken());
    }
}