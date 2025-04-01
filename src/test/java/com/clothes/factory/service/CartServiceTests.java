package com.clothes.factory.service;

import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.repository.CartRepository;
import com.clothes.factory.repository.ClothRepository;
import com.clothes.factory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clothes.factory.object_mother.CartMother.createCart;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

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
        Cart cart = createCart(new BigDecimal(100));

        User user = createUser1();
        user.setCart(cart);

        when(userRepository.findById(9L))
                .thenReturn(Optional.of(user));

        //When
        Cart cartByUser = cartService.getCartByUser(9L);

        //Then
        assertEquals(9L, cartByUser.getId());
        assertEquals(new BigDecimal(100), cartByUser.getTotalPrice());
        assertEquals(0, cartByUser.getClothesList().size());
    }

    @Test
    public void testAddClothToCart() throws ClothNotFoundException, CartNotFoundException {
        //Given
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
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
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        List<Cloth> clothList = new ArrayList<>();
        clothList.add(cloth1);
        clothList.add(cloth2);
        cart.setClothesList(clothList);

        when(cartRepository.findById(9L))
                .thenReturn(Optional.of(cart));

        //When
        Cart responseCart = cartService.removeClothFromCart(9L, 2L);

        //Then
        assertEquals(1, responseCart.getClothesList().size());
        assertTrue(responseCart.getClothesList().contains(cloth1));
        assertFalse(responseCart.getClothesList().contains(cloth2));
    }

}
