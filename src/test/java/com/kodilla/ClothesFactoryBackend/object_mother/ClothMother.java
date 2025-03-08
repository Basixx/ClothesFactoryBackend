package com.kodilla.ClothesFactoryBackend.object_mother;

import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClothMother {

    public static Cloth createCloth1() {
        return Cloth.builder()
                .id(1L)
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

    public static Cloth createCloth2() {
        return Cloth.builder()
                .id(2L)
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

    public static ClothDto createClothDto() {
        return ClothDto.builder()
                .id(1L)
                .fashion(Fashion.LONGSLEEVE)
                .print("MyPrint")
                .color(Color.RED)
                .printColor(Color.WHITE)
                .font(Font.ARIAL)
                .size(Size.M)
                .quantity(1)
                .price(new BigDecimal(70))
                .build();
    }

    public static List<ClothDto> createClothDtoList() {
        List<ClothDto> clothesDto = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            clothesDto.add(ClothDto.builder()
                    .id(i + 1L)
                    .fashion(Fashion.LONGSLEEVE)
                    .print("MyPrint " + i)
                    .color(Color.RED)
                    .printColor(Color.WHITE)
                    .font(Font.ARIAL)
                    .size(Size.M)
                    .quantity(1)
                    .price(new BigDecimal(70))
                    .build());
        }
        return clothesDto;
    }

}