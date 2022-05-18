package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@Transactional
public class ClothRepositoryTestSuite {
    @Autowired
    private ClothRepository clothRepository;

    @Test
    void testSaveCloth() {
        //Given
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();

        //When
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);

        List<Cloth> clothesInDb = clothRepository.findAll();

        //Then
        assertEquals(2, clothesInDb.size());
    }

    @Test
    void testDeleteCloth() {
        //Given
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        cloth2.setId(null);
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);
        Long id = cloth2.getId();

        //When
        clothRepository.deleteById(id);

        int clothesCount = clothRepository.findAll().size();

        //Then
        assertEquals(1, clothesCount);
    }
}
