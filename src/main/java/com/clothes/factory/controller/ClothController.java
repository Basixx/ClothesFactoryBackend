package com.clothes.factory.controller;

import com.clothes.factory.domain.ClothDto;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.ClothFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clothes")
@RequiredArgsConstructor
public class ClothController {

    private final ClothFacade clothFacade;

    @GetMapping
    public ResponseEntity<List<ClothDto>> getAllClothes() {
        return ResponseEntity.ok(clothFacade.getAllClothes());
    }

    @GetMapping(value = "/fromUserCart/{userId}")
    public ResponseEntity<List<ClothDto>> getClothesFromUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(clothFacade.getClothesFromUserCart(userId));
    }

    @GetMapping(value = "/fromOrder/{orderId}")
    public ResponseEntity<List<ClothDto>> getClothesFromOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(clothFacade.getClothesFromOrder(orderId));
    }

    @PostMapping
    public ResponseEntity<ClothDto> createCloth(@RequestBody ClothDto clothDto) throws ProfanityCheckFailedException, ClothPrintContainsBadWordsException, ClothWithQuantityZeroException {
        return ResponseEntity.ok(clothFacade.createCloth(clothDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClothDto> updateCloth(@PathVariable Long id, @RequestBody ClothDto clothDto) throws ClothNotFoundException, ProfanityCheckFailedException, ClothPrintContainsBadWordsException {
        return ResponseEntity.ok(clothFacade.updateCloth(id, clothDto));
    }

}
