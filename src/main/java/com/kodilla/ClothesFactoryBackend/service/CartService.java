package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ClothRepository clothRepository;

    public Cart getCartByUser(final Long userId) throws UserNotFoundException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userFromDb.getCart();
    }

    public Cart addClothToCart(final Long cartId, final Long clothId) throws CartNotFoundException, ClothNotFoundException {
        Cart cartFromDb = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        Cloth clothFromDb = clothRepository.findById(clothId).orElseThrow(ClothNotFoundException::new);
        clothFromDb.setCart(cartFromDb);
        cartFromDb.getClothesList().add(clothFromDb);
        BigDecimal price = cartFromDb.getClothesList().stream().map(Cloth::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        cartFromDb.setTotalPrice(price);
        return cartFromDb;
    }

    public Cart removeClothFromCart(final Long cartId, final Long clothId) throws CartNotFoundException, ClothNotFoundException {
        Cart cartFromDb = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        Cloth clothFromCart = cartFromDb.getClothesList().stream()
                .filter(cloth -> cloth.getId().equals(clothId))
                .findFirst()
                .orElseThrow(ClothNotFoundException::new);
        cartFromDb.getClothesList().remove(clothFromCart);
        BigDecimal price = cartFromDb.getClothesList().stream().map(Cloth::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        cartFromDb.setTotalPrice(price);
        clothRepository.delete(clothFromCart);
        return cartFromDb;
    }

}
