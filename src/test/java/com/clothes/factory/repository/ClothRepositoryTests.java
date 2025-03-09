package com.clothes.factory.repository;

import com.clothes.factory.domain.Cloth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ClothRepositoryTests {

    @Autowired
    private ClothRepository clothRepository;

    @Test
    void testSaveCloth() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();

        cloth1.setId(null);
        cloth2.setId(null);

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
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        cloth1.setId(null);
        cloth2.setId(null);
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);
        Long id = clothRepository.findAll()
                .stream()
                .map(Cloth::getId)
                .findFirst()
                .orElseThrow();

        //When
        clothRepository.deleteById(id);
        int clothesCount = clothRepository.findAll().size();

        //Then
        assertEquals(1, clothesCount);
    }

}
