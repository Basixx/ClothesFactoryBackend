package com.clothes.factory.controller;

import com.clothes.factory.domain.CartDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.CartFacade;
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
        return ResponseEntity.ok(cartFacade.deleteClothFromCart(idCart, idCloth));
    }

}
