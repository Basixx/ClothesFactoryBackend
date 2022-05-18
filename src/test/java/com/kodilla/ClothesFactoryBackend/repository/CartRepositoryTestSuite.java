package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import com.kodilla.ClothesFactoryBackend.object_mother.UserMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CartRepositoryTestSuite {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClothRepository clothRepository;


    @Test
    void testSaveCartWithUser() {
        //Given
        User user = UserMother.createUser1();
        user.setId(null);
        Cart cart = CartMother.createCart(new BigDecimal(50));
        cart.setId(null);
        user.setCart(cart);

        //When
        userRepository.save(user);
        Long userId = user.getId();
        cartRepository.save(cart);
        Long cartId = cart.getId();

        //Then
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, userRepository.findAll().size());
        assertEquals(cartId, userRepository.findById(userId).get().getCart().getId());
    }

    @Test
    void testCartHasClothes() {
        //Given
        Cart cart = Cart.builder().clothesList(new ArrayList<>()).totalPrice(new BigDecimal(50)).build();

        Cloth cloth1 = ClothMother.createCloth1();
        cloth1.setId(null);

        Cloth cloth2 = ClothMother.createCloth2();
        cloth2.setId(null);

        cart.getClothesList().add(cloth1);
        cart.getClothesList().add(cloth2);

        //When
        cartRepository.save(cart);
        Long cartId = cart.getId();

        clothRepository.save(cloth1);

        clothRepository.save(cloth2);

        List<Cart> carts = cartRepository.findAll();
        List<Cloth> clothes = clothRepository.findAll();

        //Then
        assertEquals(1, carts.size());
        assertEquals(2, clothes.size());
        assertEquals(2, cartRepository.findById(cartId).get().getClothesList().size());

        assertTrue(cartRepository.findById(cartId).get().getClothesList().contains(cloth1));
        assertTrue(cartRepository.findById(cartId).get().getClothesList().contains(cloth2));
    }
}