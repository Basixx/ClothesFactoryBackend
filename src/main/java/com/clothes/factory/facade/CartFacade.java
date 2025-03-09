package com.clothes.factory.facade;

import com.clothes.factory.domain.CartDto;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.mapper.CartMapper;
import com.clothes.factory.service.CartService;
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
