package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.cloth.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import com.kodilla.ClothesFactoryBackend.object_mother.UserMother;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTestSuite {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClothRepository clothRepository;

    @Test
    public void testGetCartByUser() throws UserNotFoundException {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(100));

        User user = UserMother.createUser1();
        user.setCart(cart);

        when(userRepository.findById(9L)).thenReturn(Optional.of(user));

        //When
        Cart cartByUser = cartService.getCartByUser(9L);

        //Then
        assertEquals(9L, cartByUser.getId());
        assertEquals(new BigDecimal(100), cartByUser.getTotalPrice());
        assertEquals(0,cartByUser.getClothesList().size());
    }

    @Test
    public void testAddClothToCart() throws ClothNotFoundException, CartNotFoundException {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        List<Cloth> clothList = new ArrayList<>();
        clothList.add(cloth1);
        cart.setClothesList(clothList);

        when(cartRepository.findById(9L)).thenReturn(Optional.of(cart));
        when(clothRepository.findById(2L)).thenReturn(Optional.ofNullable(cloth2));

        //When
        Cart responseCart = cartService.addClothToCart(9L, 2L);

        //Then
        assertEquals(2, responseCart.getClothesList().size());
        assertTrue(responseCart.getClothesList().contains(cloth2));
    }

    @Test
    public void testRemoveClothFromCart() throws ClothNotFoundException, CartNotFoundException {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        List<Cloth> clothList = new ArrayList<>();
        clothList.add(cloth1);
        clothList.add(cloth2);
        cart.setClothesList(clothList);

        when(cartRepository.findById(9L)).thenReturn(Optional.of(cart));

        //When
        Cart responseCart = cartService.removeClothFromCart(9L, 2L);

        //Then
        assertEquals(1, responseCart.getClothesList().size());
        assertTrue(responseCart.getClothesList().contains(cloth1));
        assertFalse(responseCart.getClothesList().contains(cloth2));
    }
}