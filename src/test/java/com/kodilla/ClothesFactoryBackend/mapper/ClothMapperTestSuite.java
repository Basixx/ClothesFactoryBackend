package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.auxiliary.*;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
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
    void testMapToCloth() {
        //Given
        ClothDto clothDto = ClothMother.createClothDto();

        //When
        Cloth cloth = clothMapper.mapToCloth(clothDto);

        //Then
        assertEquals(Fashion.LONGSLEEVE, cloth.getFashion());
        assertEquals(Color.RED, cloth.getColor());
        assertEquals("MyPrint", cloth.getPrint());
        assertEquals(Font.ARIAL, cloth.getFont());
        assertEquals(Color.WHITE, cloth.getPrintColor());
        assertEquals(Size.M, cloth.getSize());
        assertEquals(1, cloth.getQuantity());
        assertEquals(new BigDecimal(70), cloth.getPrice());
    }

    @Test
    void testMapToClothDto() {
        //Given
        Cloth cloth = ClothMother.createCloth1();

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
    void testMapToClothDtoList() {
        //Given
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();

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
    void testMapToClothesFromIds() throws ClothNotFoundException {
        //Given
        List<Long> clothesIds = new ArrayList<>();
        clothesIds.add(1L);
        clothesIds.add(2L);

        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
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
    void mapToClothesIdsFromClothes() {
        //Given
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();

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
}