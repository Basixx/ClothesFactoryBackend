package com.clothes.factory.controller;

import com.clothes.factory.domain.CartDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.CartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/carts/{idCart}/clothes/{idCloth}")
    @ResponseStatus(OK)
    public CartDto deleteClothFromCart(@PathVariable Long idCart, @PathVariable Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return cartFacade.deleteClothFromCart(idCart, idCloth);
    }

}
