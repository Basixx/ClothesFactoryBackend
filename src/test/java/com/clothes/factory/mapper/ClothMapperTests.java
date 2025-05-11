package com.clothes.factory.mapper;

import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.ClothDto;
import com.clothes.factory.repository.ClothRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.clothes.factory.auxiliary.Color.BLACK;
import static com.clothes.factory.auxiliary.Color.RED;
import static com.clothes.factory.auxiliary.Color.WHITE;
import static com.clothes.factory.auxiliary.Fashion.HOODIE;
import static com.clothes.factory.auxiliary.Fashion.LONGSLEEVE;
import static com.clothes.factory.auxiliary.Fashion.T_SHIRT;
import static com.clothes.factory.auxiliary.Font.ARIAL;
import static com.clothes.factory.auxiliary.Font.COMIC_SANS;
import static com.clothes.factory.auxiliary.Size.M;
import static com.clothes.factory.auxiliary.Size.XXL;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static com.clothes.factory.object_mother.ClothMother.createClothDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClothMapperTests {

    @Mock
    private ClothRepository clothRepository;

    @InjectMocks
    private ClothMapper clothMapper;

    @Test
    void testMapToCloth() {
        //Given
        ClothDto clothDto = createClothDto();

        //When
        Cloth cloth = clothMapper.mapToCloth(clothDto);

        //Then
        assertEquals(LONGSLEEVE, cloth.getFashion());
        assertEquals(RED, cloth.getColor());
        assertEquals("MyPrint", cloth.getPrint());
        assertEquals(ARIAL, cloth.getFont());
        assertEquals(WHITE, cloth.getPrintColor());
        assertEquals(M, cloth.getSize());
        assertEquals(1, cloth.getQuantity());
        assertEquals(new BigDecimal(70), cloth.getPrice());
    }

    @Test
    void testMapToClothDto() {
        //Given
        Cloth cloth = createCloth1();

        //When
        ClothDto clothDto = clothMapper.mapToClothDto(cloth);

        //Then
        assertEquals(1L, clothDto.getId());
        assertEquals(HOODIE, clothDto.getFashion());
        assertEquals(RED, clothDto.getColor());
        assertEquals("hello", clothDto.getPrint());
        assertEquals(ARIAL, clothDto.getFont());
        assertEquals(BLACK, clothDto.getPrintColor());
        assertEquals(M, clothDto.getSize());
        assertEquals(2, clothDto.getQuantity());
        assertEquals(new BigDecimal(200), clothDto.getPrice());
    }

    @Test
    void testMapToClothDtoList() {
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

        assertEquals(1L, clothDtoList.getFirst().getId());
        assertEquals(HOODIE, clothDtoList.getFirst().getFashion());
        assertEquals(RED, clothDtoList.getFirst().getColor());
        assertEquals("hello", clothDtoList.getFirst().getPrint());
        assertEquals(ARIAL, clothDtoList.getFirst().getFont());
        assertEquals(BLACK, clothDtoList.getFirst().getPrintColor());
        assertEquals(M, clothDtoList.getFirst().getSize());
        assertEquals(2, clothDtoList.getFirst().getQuantity());
        assertEquals(new BigDecimal(200), clothDtoList.getFirst().getPrice());

        assertEquals(2L, clothDtoList.get(1).getId());
        assertEquals(T_SHIRT, clothDtoList.get(1).getFashion());
        assertEquals(BLACK, clothDtoList.get(1).getColor());
        assertEquals("drama", clothDtoList.get(1).getPrint());
        assertEquals(COMIC_SANS, clothDtoList.get(1).getFont());
        assertEquals(WHITE, clothDtoList.get(1).getPrintColor());
        assertEquals(XXL, clothDtoList.get(1).getSize());
        assertEquals(3, clothDtoList.get(1).getQuantity());
        assertEquals(new BigDecimal(150), clothDtoList.get(1).getPrice());
    }

    @Test
    void mapToClothesIdsFromClothes() {
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

        assertEquals(1L, ids.getFirst());
        assertEquals(2L, ids.get(1));
    }

}
