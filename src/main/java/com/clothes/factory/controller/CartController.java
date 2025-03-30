package com.clothes.factory.controller;

import com.clothes.factory.domain.CartDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.CartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    @GetMapping(value = "/users/{userId}/cart")
    @ResponseStatus(OK)
    public CartDto getUserCart(@PathVariable Long userId) throws UserNotFoundException {
        return cartFacade.getUserCart(userId);
    }

    @PutMapping("/carts/{idCart}/clothes/{idCloth}")
    @ResponseStatus(OK)
    public CartDto addClothToCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return cartFacade.addClothToCart(idCart, idCloth);
    }

    @DeleteMapping("carts/{idCart}/clothes/{idCloth}")
    @ResponseStatus(OK)
    public CartDto deleteClothFromCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return cartFacade.deleteClothFromCart(idCart, idCloth);
    }

}
