package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.ClothDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.ClothMapper;
import com.kodilla.ClothesFactoryBackend.service.ClothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/clothes")
@RequiredArgsConstructor
public class ClothController {
    private final ClothService clothService;
    private final ClothMapper clothMapper;

    @GetMapping
    public ResponseEntity<List<ClothDto>> getClothes() {
        return ResponseEntity.ok(clothMapper.mapToClothDtoList(clothService.getAllClothes()));
    }

    @GetMapping(value = "/fromUserCart/{userId}")
    public ResponseEntity<List<ClothDto>> getClothesFromUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(clothMapper.mapToClothDtoList(clothService.getAllClothesFromUsersCart(userId)));
    }

    @GetMapping(value = "/fromOrder/{orderId}")
    public ResponseEntity<List<ClothDto>> getClothesFromOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(clothMapper.mapToClothDtoList(clothService.getAllClothesFromOrder(orderId)));
    }

    @PostMapping
    public ResponseEntity<ClothDto> createCloth(@RequestBody ClothDto clothDto) {
        return ResponseEntity.ok(clothMapper.mapToClothDto(clothService.createCloth(clothMapper.mapToCloth(clothDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClothDto> updateCloth(@PathVariable Long id, @RequestBody ClothDto clothDto) throws OrderNotFoundException, CartNotFoundException, ClothNotFoundException {
        return ResponseEntity.ok(clothMapper.mapToClothDto(clothService.editCloth(id, clothMapper.mapToCloth(clothDto))));
    }
}