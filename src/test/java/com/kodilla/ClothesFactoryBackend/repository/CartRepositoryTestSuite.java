package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.User;
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
    public void testSaveCartWithUser() {
        //Given
        User user = User.builder()
                .name("John")
                .surname("Smith")
                .phoneNumber("111111111")
                .emailAddress("john@smith.com")
                .password("password")
                .build();
        Cart cart = Cart.builder()
                .clothesList(new ArrayList<>())
                .totalPrice(new BigDecimal(50))
                .build();
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
    public void testCartHasClothes() {
        //Given
        Cart cart = Cart.builder().clothesList(new ArrayList<>()).totalPrice(new BigDecimal(50)).build();

        Cloth cloth1 = Cloth.builder()
                .fashion(Fashion.HOODIE)
                .color(Color.RED)
                .print("hello")
                .font(Font.ARIAL)
                .printColor(Color.BLACK)
                .size(Size.M)
                .quantity(2)
                .price(new BigDecimal(200))
                .build();

        Cloth cloth2 = Cloth.builder()
                .fashion(Fashion.T_SHIRT)
                .color(Color.BLACK)
                .print("drama")
                .font(Font.COMIC_SANS)
                .printColor(Color.WHITE)
                .size(Size.XXL)
                .quantity(3)
                .price(new BigDecimal(150))
                .build();

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