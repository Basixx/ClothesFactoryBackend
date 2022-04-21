package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.CartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartFacade cartFacade;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<CartDto> getUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(cartFacade.getUserCart(userId));
    }

    @PutMapping("/addCloth/{idCart}/{idCloth}")
    public ResponseEntity<CartDto> addClothToCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartFacade.addClothToCart(idCart, idCloth));
    }

    @PutMapping("/{idCart}/{idCloth}")
    public ResponseEntity<CartDto> deleteClothFromCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartFacade.addClothToCart(idCart, idCloth));
    }
}