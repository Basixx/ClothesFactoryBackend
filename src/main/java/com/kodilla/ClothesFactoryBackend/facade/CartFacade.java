package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.CartDto;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.CartMapper;
import com.kodilla.ClothesFactoryBackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartDto getUserCart(Long userId) throws UserNotFoundException {
        return cartMapper.mapToCartDto(cartService.getCartByUser(userId));
    }

    public CartDto addClothToCart(Long idCart, Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return cartMapper.mapToCartDto(cartService.addClothToCart(idCart, idCloth));
    }

    public CartDto deleteClothFromCart(Long idCart, Long idCloth) throws ClothNotFoundException, CartNotFoundException {
        return cartMapper.mapToCartDto(cartService.removeClothFromCart(idCart, idCloth));
    }

}
