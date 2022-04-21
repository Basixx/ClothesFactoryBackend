package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.ClothMapper;
import com.kodilla.ClothesFactoryBackend.service.ClothService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothFacade {

    private final ClothMapper clothMapper;

    private final ClothService clothService;

    public List<ClothDto> getClothes(){
        return clothMapper.mapToClothDtoList(clothService.getAllClothes());
    }

    public List<ClothDto> getClothesFromUserCart(Long userId) throws UserNotFoundException {
       return clothMapper.mapToClothDtoList(clothService.getAllClothesFromUsersCart(userId));
    }

    public List<ClothDto> getClothesFromOrder(Long orderId) throws OrderNotFoundException {
        return clothMapper.mapToClothDtoList(clothService.getAllClothesFromOrder(orderId));
    }

    public ClothDto createCloth(ClothDto clothDto) {
        return clothMapper.mapToClothDto(clothService.createCloth(clothMapper.mapToCloth(clothDto)));
    }

    public ClothDto updateCloth(Long id, ClothDto clothDto) throws ClothNotFoundException {
        return clothMapper.mapToClothDto(clothService.editCloth(id, clothMapper.mapToCloth(clothDto)));
    }
}