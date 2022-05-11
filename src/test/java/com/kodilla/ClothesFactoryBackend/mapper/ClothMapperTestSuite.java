package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.auxiliary.*;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClothMapperTestSuite {

    @Mock
    private ClothRepository clothRepository;
    @InjectMocks
    private  ClothMapper clothMapper;

    @Test
    public void testMapToCloth() {
        //Given
        ClothDto clothDto = ClothDto.builder()
                .fashion(Fashion.HOODIE)
                .color(Color.RED)
                .print("hello")
                .font(Font.ARIAL)
                .printColor(Color.BLACK)
                .size(Size.M)
                .quantity(2)
                .build();

        //When
        Cloth cloth = clothMapper.mapToCloth(clothDto);

        //Then
        assertEquals(Fashion.HOODIE, cloth.getFashion());
        assertEquals(Color.RED, cloth.getColor());
        assertEquals("hello", cloth.getPrint());
        assertEquals(Font.ARIAL, cloth.getFont());
        assertEquals(Color.BLACK, cloth.getPrintColor());
        assertEquals(Size.M, cloth.getSize());
        assertEquals(2, cloth.getQuantity());
        assertEquals(new BigDecimal(200), cloth.getPrice());
    }

    @Test
    public void testMapToClothDto() {
        //Given
        Cloth cloth = createCloth1();

        //When
        ClothDto clothDto = clothMapper.mapToClothDto(cloth);

        //Then
        assertEquals(1L, clothDto.getId());
        assertEquals(Fashion.HOODIE, clothDto.getFashion());
        assertEquals(Color.RED, clothDto.getColor());
        assertEquals("hello", clothDto.getPrint());
        assertEquals(Font.ARIAL, clothDto.getFont());
        assertEquals(Color.BLACK, clothDto.getPrintColor());
        assertEquals(Size.M, clothDto.getSize());
        assertEquals(2, clothDto.getQuantity());
        assertEquals(new BigDecimal(200), clothDto.getPrice());
    }

    @Test
    public void testMapToClothDtoList() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();

        List<Cloth> clothes = new ArrayList<>();
        clothes.add(cloth1);
        clothes.add(cloth2);

        //When
        List<ClothDto> clothDtoList = clothMapper.mapToClothDtoList(clothes);

        //Then
        assertEquals(2, clothDtoList.size());

        assertEquals(1L, clothDtoList.get(0).getId());
        assertEquals(Fashion.HOODIE, clothDtoList.get(0).getFashion());
        assertEquals(Color.RED, clothDtoList.get(0).getColor());
        assertEquals("hello", clothDtoList.get(0).getPrint());
        assertEquals(Font.ARIAL, clothDtoList.get(0).getFont());
        assertEquals(Color.BLACK, clothDtoList.get(0).getPrintColor());
        assertEquals(Size.M, clothDtoList.get(0).getSize());
        assertEquals(2, clothDtoList.get(0).getQuantity());
        assertEquals(new BigDecimal(200), clothDtoList.get(0).getPrice());

        assertEquals(2L, clothDtoList.get(1).getId());
        assertEquals(Fashion.T_SHIRT, clothDtoList.get(1).getFashion());
        assertEquals(Color.BLACK, clothDtoList.get(1).getColor());
        assertEquals("drama", clothDtoList.get(1).getPrint());
        assertEquals(Font.COMIC_SANS, clothDtoList.get(1).getFont());
        assertEquals(Color.WHITE, clothDtoList.get(1).getPrintColor());
        assertEquals(Size.XXL, clothDtoList.get(1).getSize());
        assertEquals(3, clothDtoList.get(1).getQuantity());
        assertEquals(new BigDecimal(150), clothDtoList.get(1).getPrice());
    }

    @Test
    public void testMapToClothesFromIds() throws ClothNotFoundException {
        //Given
        List<Long> clothesIds = new ArrayList<>();
        clothesIds.add(1L);
        clothesIds.add(2L);

        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        when(clothRepository.findById(1L)).thenReturn(Optional.of(cloth1));
        when(clothRepository.findById(2L)).thenReturn(Optional.of(cloth2));

        //When
        List<Cloth> clothes = clothMapper.mapToClothesFromIds(clothesIds);

        //Then
        assertEquals(2, clothes.size());
        assertEquals(1L, clothes.get(0).getId());
        assertEquals(2L, clothes.get(1).getId());
    }

    @Test
    public void mapToClothesIdsFromClothes() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();

        List<Cloth> clothes = new ArrayList<>();
        clothes.add(cloth1);
        clothes.add(cloth2);

        //When
        List<Long> ids = clothMapper.mapToClothesIdsFromClothes(clothes);

        //Then
        assertEquals(2, ids.size());

        assertEquals(1L, ids.get(0));
        assertEquals(2L, ids.get(1));
    }

    private Cloth createCloth1(){
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

    private Cloth createCloth2(){
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
}