package com.clothes.factory.service;

import com.clothes.factory.domain.AdminToken;
import com.clothes.factory.mail.AdminMailCreator;
import com.clothes.factory.repository.AdminTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTokenServiceTests {

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
        when(adminTokenRepository.existsByToken("ABCDEFG"))
                .thenReturn(true);
        when(adminTokenRepository.existsByToken("KLMNOP"))
                .thenReturn(false);
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
        doNothing().when(adminTokenRepository)
                .deleteAll();
        when(adminTokenRepository.save(any()))
                .thenReturn(AdminToken.builder().id(5L).token("ABCDEF").build());

        //When
        AdminToken adminToken = adminTokenService.saveToken();

        //Then
        verify(emailService, times(1)).send(any());
        assertEquals(5L, adminToken.getId());
        assertEquals("ABCDEF", adminToken.getToken());
    }

}
