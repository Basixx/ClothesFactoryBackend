package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClothMapperTestSuite {
    private ClothMapper clothMapper = new ClothMapper();

    @Test
    public void testMapToCloth() {
        //Given
        ClothDto clothDto = ClothDto.builder()
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .build();

        //When
        Cloth cloth = clothMapper.mapToCloth(clothDto);

        //Then
        assertEquals("Hoodie", cloth.getFashion());
        assertEquals("red", cloth.getColor());
        assertEquals("hello", cloth.getPrint());
        assertEquals("Comic sans", cloth.getFont());
        assertEquals("white", cloth.getPrintColor());
        assertEquals("M", cloth.getSize());
        assertEquals(2, cloth.getQuantity());
        assertEquals(new BigDecimal(200), cloth.getPrice());
    }

    @Test
    public void testMapToClothDto() {
        //Given
        Cloth cloth = Cloth.builder()
                .id(1L)
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .price(new BigDecimal(200))
                .build();

        //When
        ClothDto clothDto = clothMapper.mapToClothDto(cloth);

        //Then
        assertEquals(1L, clothDto.getId());
        assertEquals("Hoodie", clothDto.getFashion());
        assertEquals("red", clothDto.getColor());
        assertEquals("hello", clothDto.getPrint());
        assertEquals("Comic sans", clothDto.getFont());
        assertEquals("white", clothDto.getPrintColor());
        assertEquals("M", clothDto.getSize());
        assertEquals(2, clothDto.getQuantity());
        assertEquals(new BigDecimal(200), clothDto.getPrice());
    }

    @Test
    public void testMapToClothDtoList() {
        //Given
        Cloth cloth = Cloth.builder()
                .id(1L)
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .price(new BigDecimal(200))
                .build();

        Cloth cloth2 = Cloth.builder()
                .id(2L)
                .fashion("T-Shirt")
                .color("red")
                .print("drama")
                .font("Arial")
                .printColor("black")
                .size("XL")
                .quantity(3)
                .price(new BigDecimal(150))
                .build();

        List<Cloth> clothList = new ArrayList<>();
        clothList.add(cloth);
        clothList.add(cloth2);

        //When
        List<ClothDto> clothDtoList = clothMapper.mapToClothDtoList(clothList);

        //Then
        assertEquals(2, clothDtoList.size());

        assertEquals(1L, clothDtoList.get(0).getId());
        assertEquals("Hoodie", clothDtoList.get(0).getFashion());
        assertEquals("red", clothDtoList.get(0).getColor());
        assertEquals("hello", clothDtoList.get(0).getPrint());
        assertEquals("Comic sans", clothDtoList.get(0).getFont());
        assertEquals("white", clothDtoList.get(0).getPrintColor());
        assertEquals("M", clothDtoList.get(0).getSize());
        assertEquals(2, clothDtoList.get(0).getQuantity());
        assertEquals(new BigDecimal(200), clothDtoList.get(0).getPrice());

        assertEquals(2L, clothDtoList.get(1).getId());
        assertEquals("T-Shirt", clothDtoList.get(1).getFashion());
        assertEquals("red", clothDtoList.get(1).getColor());
        assertEquals("drama", clothDtoList.get(1).getPrint());
        assertEquals("Arial", clothDtoList.get(1).getFont());
        assertEquals("black", clothDtoList.get(1).getPrintColor());
        assertEquals("XL", clothDtoList.get(1).getSize());
        assertEquals(3, clothDtoList.get(1).getQuantity());
        assertEquals(new BigDecimal(150), clothDtoList.get(1).getPrice());
    }

    @Test
    public void mapToClothesIdsFromClothes() {
        //Given
        Cloth cloth3 = Cloth.builder()
                .id(3L)
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .price(new BigDecimal(200))
                .build();



        Cloth cloth5 = Cloth.builder()
                .id(5L)
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .price(new BigDecimal(200))
                .build();


        Cloth cloth8 = Cloth.builder()
                .id(8L)
                .fashion("Hoodie")
                .color("red")
                .print("hello")
                .font("Comic sans")
                .printColor("white")
                .size("M")
                .quantity(2)
                .price(new BigDecimal(200))
                .build();

        List<Cloth> clothList = new ArrayList<>();
        clothList.add(cloth3);
        clothList.add(cloth5);
        clothList.add(cloth8);

        //When
        List<Long> ids = clothMapper.mapToClothesIdsFromClothes(clothList);

        //Then
        assertEquals(3, ids.size());

        assertEquals(3L, ids.get(0));
        assertEquals(5L, ids.get(1));
        assertEquals(8L, ids.get(2));
    }
}
