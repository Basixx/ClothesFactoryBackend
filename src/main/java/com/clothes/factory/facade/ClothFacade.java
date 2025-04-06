package com.clothes.factory.facade;

import com.clothes.factory.domain.ClothDto;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.mapper.ClothMapper;
import com.clothes.factory.service.ClothService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothFacade {

    private final ClothMapper clothMapper;
    private final ClothService clothService;

    public List<ClothDto> getAllClothes(int page, int size) {
        return clothMapper.mapToClothDtoList(clothService.getAllClothes(page, size));
    }

    public List<ClothDto> getClothesFromUserCart(Long userId) throws UserNotFoundException {
        return clothMapper.mapToClothDtoList(clothService.getAllClothesFromUsersCart(userId));
    }

    public List<ClothDto> getClothesFromOrder(Long orderId) throws OrderNotFoundException {
        return clothMapper.mapToClothDtoList(clothService.getAllClothesFromOrder(orderId));
    }

    public ClothDto createCloth(ClothDto clothDto) throws ProfanityCheckFailedException, ClothPrintContainsBadWordsException, ClothWithQuantityZeroException {
        return clothMapper.mapToClothDto(clothService.createCloth(clothMapper.mapToCloth(clothDto)));
    }

    public ClothDto updateCloth(Long id, ClothDto clothDto) throws ClothNotFoundException, ProfanityCheckFailedException, ClothPrintContainsBadWordsException {
        return clothMapper.mapToClothDto(clothService.editCloth(id, clothMapper.mapToCloth(clothDto)));
    }

}
