package com.clothes.factory.service;

import com.clothes.factory.auxiliary.Prices;
import com.clothes.factory.auxiliary.shipment.strategy.companies.Fedex;
import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.api.ProfanityCheckFailedException;
import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.repository.ClothRepository;
import com.clothes.factory.repository.OrderRepository;
import com.clothes.factory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.clothes.factory.auxiliary.Color.BLACK;
import static com.clothes.factory.auxiliary.Color.RED;
import static com.clothes.factory.auxiliary.Color.WHITE;
import static com.clothes.factory.auxiliary.Fashion.HOODIE;
import static com.clothes.factory.auxiliary.Fashion.T_SHIRT;
import static com.clothes.factory.auxiliary.Font.ARIAL;
import static com.clothes.factory.auxiliary.Font.COMIC_SANS;
import static com.clothes.factory.auxiliary.Size.M;
import static com.clothes.factory.auxiliary.Size.XXL;
import static com.clothes.factory.object_mother.CartMother.createCart;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static com.clothes.factory.object_mother.OrderMother.createOrder;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClothServiceTests {

    @InjectMocks
    private ClothService clothService;

    @Mock
    private ClothRepository clothRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BadWordsService badWordsService;

    @Mock
    private Prices prices;

    @Test
    void testGetAllClothes() {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        when(clothRepository.findAll())
                .thenReturn(clothesList);

        //When
        List<Cloth> resultClothList = clothService.getAllClothes();

        //Then
        assertEquals(2, resultClothList.size());
        assertTrue(resultClothList.contains(cloth1));
        assertTrue(resultClothList.contains(cloth2));
    }

    @Test
    void testGetAllClothesFromUsersCart() throws UserNotFoundException {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        Cart cart = createCart(new BigDecimal(50));
        cart.setClothesList(clothesList);

        User user = createUser1();
        user.setCart(cart);

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        //When
        List<Cloth> resultList = clothService.getAllClothesFromUsersCart(6L);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(cloth1));
        assertTrue(resultList.contains(cloth2));
    }

    @Test
    void testGetAllClothesFromOrder() throws OrderNotFoundException {
        //Given
        Cloth cloth1 = createCloth1();
        Cloth cloth2 = createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        User user = createUser1();
        Order order = createOrder(8L, user, new BigDecimal(100), new Fedex());

        order.setClothesList(clothesList);

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        //When
        List<Cloth> resultList = clothService.getAllClothesFromOrder(8L);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(cloth1));
        assertTrue(resultList.contains(cloth2));
    }

    @Test
    void testCreateCloth() throws ProfanityCheckFailedException, ClothPrintContainsBadWordsException, ClothWithQuantityZeroException {
        //Given
        Cloth cloth = createCloth1();
        cloth.setId(null);

        when(badWordsService.containsBadWords(anyString()))
                .thenReturn(false);
        when(clothRepository.save(any()))
                .thenReturn(cloth);

        //When
        Cloth resultCloth = clothService.createCloth(cloth);

        //Then
        assertEquals(HOODIE, resultCloth.getFashion());
        assertEquals(RED, resultCloth.getColor());
        assertEquals("hello", resultCloth.getPrint());
        assertEquals(ARIAL, resultCloth.getFont());
        assertEquals(BLACK, resultCloth.getPrintColor());
        assertEquals(M, resultCloth.getSize());
        assertEquals(2, resultCloth.getQuantity());
        assertEquals(new BigDecimal(200), resultCloth.getPrice());
    }

    @Test
    void testEditCloth() throws ProfanityCheckFailedException, ClothNotFoundException, ClothPrintContainsBadWordsException {
        //Given
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth = createCloth1();

        when(clothRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(cloth));
        when(badWordsService.containsBadWords(anyString()))
                .thenReturn(false);
        when(prices.findClothPrice(any()))
                .thenReturn(new BigDecimal(100));

        Cloth cloth2 = createCloth2();
        cloth2.setId(1L);

        assert cloth != null;
        cloth.setCart(cart);
        cart.setClothesList(List.of(cloth));

        //When
        Cloth resultCloth = clothService.editCloth(1L, cloth2);

        //Then
        assertEquals(T_SHIRT, resultCloth.getFashion());
        assertEquals(BLACK, resultCloth.getColor());
        assertEquals("drama", resultCloth.getPrint());
        assertEquals(COMIC_SANS, resultCloth.getFont());
        assertEquals(WHITE, resultCloth.getPrintColor());
        assertEquals(XXL, resultCloth.getSize());
        assertEquals(3, resultCloth.getQuantity());
        assertEquals(new BigDecimal(300), resultCloth.getPrice());
    }

}
