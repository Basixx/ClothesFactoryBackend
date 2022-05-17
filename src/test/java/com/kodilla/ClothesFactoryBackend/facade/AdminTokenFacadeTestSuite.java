package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.AdminToken;
import com.kodilla.ClothesFactoryBackend.repository.AdminTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AdminTokenFacadeTestSuite {
    @Autowired
    private AdminTokenFacade adminTokenFacade;
    @Autowired
    private AdminTokenRepository adminTokenRepository;

    @Test
    void testExistsByToken() {
        //Given
        AdminToken adminToken = AdminToken.builder().token("ABCDEFG").build();
        adminTokenRepository.save(adminToken);

        //When
        boolean exists = adminTokenFacade.existsByToken("ABCDEFG");

        //Then
        assertTrue(exists);
    }

    @Test
    void testCreateToken() {
        //Given & When
        adminTokenFacade.createToken();
        int size = adminTokenRepository.findAll().size();

        //Then
        assertEquals(1, size);
    }

    @Test
    void testDeleteTokens() {
        //Given
        AdminToken adminToken = AdminToken.builder().token("ABCDEFG").build();
        adminTokenRepository.save(adminToken);

        //When
        adminTokenFacade.deleteAllTokens();
        int size = adminTokenRepository.findAll().size();

        //Then
        assertEquals(0, size);
    }
}
