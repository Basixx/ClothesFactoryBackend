package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.CartMapper;
import com.kodilla.ClothesFactoryBackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<CartDto> getUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(cartService.getCartByUser(userId)));
    }

    @PutMapping("/addCloth/{idCart}/{idCloth}")
    public ResponseEntity<CartDto> addClothToCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(cartService.addClothToCart(idCart, idCloth)));
    }

    @PutMapping("/{idCart}/{idCloth}")
    public ResponseEntity<CartDto> deleteClothFromCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(cartService.removeClothFromCart(idCart, idCloth)));
    }
}