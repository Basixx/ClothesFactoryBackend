package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
public class ClothRepositoryTestSuite {
    @Autowired
    private ClothRepository clothRepository;

    @Test
    public void testSaveCloth() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();

        //When
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);

        List<Cloth> clothesInDb = clothRepository.findAll();

        //Then
        assertEquals(2, clothesInDb.size());
    }

    @Test
    public void testDeleteCloth() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);
        Long id = cloth2.getId();

        int sizeBefore = clothRepository.findAll().size();

        //When
        clothRepository.deleteById(id);

        int sizeAfter = clothRepository.findAll().size();

        //Then
        assertEquals(2, sizeBefore);
        assertEquals(1, sizeAfter);
    }

    private Cloth createCloth1() {
        return Cloth.builder()
                .fashion(Fashion.HOODIE)
                .color(Color.RED)
                .print("hello")
                .font(Font.ARIAL)
                .printColor(Color.BLACK)
                .size(Size.M)
                .quantity(2)
                .price(new BigDecimal(200))
                .build();
    }

    private Cloth createCloth2() {
        return Cloth.builder()
                .fashion(Fashion.T_SHIRT)
                .color(Color.BLACK)
                .print("drama")
                .font(Font.COMIC_SANS)
                .printColor(Color.WHITE)
                .size(Size.XXL)
                .quantity(3)
                .price(new BigDecimal(150))
                .build();
    }
}
