package com.clothes.factory.repository;

import com.clothes.factory.BaseTest;
import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.clothes.factory.object_mother.CartMother.createCart;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CartRepositoryTests extends BaseTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClothRepository clothRepository;

    @Test
    void testSaveCartWithUser() throws UserNotFoundException {
        //Given
        User user = createUser1();
        user.setId(null);
        Cart cart = createCart(new BigDecimal(50));
        cart.setId(null);
        user.setCart(cart);

        //When
        userRepository.save(user);
        Long userId = user.getId();
        cartRepository.save(cart);
        Long cartId = cart.getId();

        User userFromDb = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        //Then
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, userRepository.findAll().size());
        assertEquals(cartId, userFromDb.getCart().getId());
    }

    @Test
    void testCartHasClothes() throws CartNotFoundException {
        //Given
        Cart cart = Cart.builder()
                .clothesList(new ArrayList<>())
                .totalPrice(new BigDecimal(50))
                .build();

        Cloth cloth1 = createCloth1();
        cloth1.setId(null);

        Cloth cloth2 = createCloth2();
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

        Cart cartFromDb = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);

        //Then
        assertEquals(1, carts.size());
        assertEquals(2, clothes.size());
        assertEquals(2, cartFromDb.getClothesList().size());

        assertTrue(cartFromDb.getClothesList().contains(cloth1));
        assertTrue(cartFromDb.getClothesList().contains(cloth2));
    }

}
