package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.auxiliary.*;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Fedex;
import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import com.kodilla.ClothesFactoryBackend.object_mother.OrderMother;
import com.kodilla.ClothesFactoryBackend.object_mother.UserMother;
import com.kodilla.ClothesFactoryBackend.repository.ClothRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClothServiceTestSuite {

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
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        when(clothRepository.findAll()).thenReturn(clothesList);

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
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        Cart cart = CartMother.createCart(new BigDecimal(50));
        cart.setClothesList(clothesList);

        User user = UserMother.createUser1();
        user.setCart(cart);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

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
        Cloth cloth1 = ClothMother.createCloth1();
        Cloth cloth2 = ClothMother.createCloth2();
        List<Cloth> clothesList = List.of(cloth1, cloth2);

        User user = UserMother.createUser1();
        Order order = OrderMother.createOrder(8L, user, new BigDecimal(100), new Fedex());

        order.setClothesList(clothesList);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

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
        Cloth cloth = ClothMother.createCloth1();
        cloth.setId(null);

        when(badWordsService.containsBadWords(anyString())).thenReturn(false);
        when(clothRepository.save(any())).thenReturn(cloth);

        //When
        Cloth resultCloth = clothService.createCloth(cloth);

        //Then
        assertEquals(Fashion.HOODIE, resultCloth.getFashion());
        assertEquals(Color.RED, resultCloth.getColor());
        assertEquals("hello", resultCloth.getPrint());
        assertEquals(Font.ARIAL, resultCloth.getFont());
        assertEquals(Color.BLACK, resultCloth.getPrintColor());
        assertEquals(Size.M, resultCloth.getSize());
        assertEquals(2, resultCloth.getQuantity());
        assertEquals(new BigDecimal(200), resultCloth.getPrice());
    }

    @Test
    void testEditCloth() throws ProfanityCheckFailedException, ClothNotFoundException, ClothPrintContainsBadWordsException {
        //Given
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth = ClothMother.createCloth1();

        when(clothRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cloth));
        when(badWordsService.containsBadWords(anyString())).thenReturn(false);
        when(prices.findClothPrice(any())).thenReturn(new BigDecimal(100));

        Cloth cloth2 = ClothMother.createCloth2();
        cloth2.setId(1L);

        assert cloth != null;
        cloth.setCart(cart);
        cart.setClothesList(List.of(cloth));

        //When
        Cloth resultCloth = clothService.editCloth(1L, cloth2);

        //Then
        assertEquals(Fashion.T_SHIRT, resultCloth.getFashion());
        assertEquals(Color.BLACK, resultCloth.getColor());
        assertEquals("drama", resultCloth.getPrint());
        assertEquals(Font.COMIC_SANS, resultCloth.getFont());
        assertEquals(Color.WHITE, resultCloth.getPrintColor());
        assertEquals(Size.XXL, resultCloth.getSize());
        assertEquals(3, resultCloth.getQuantity());
        assertEquals(new BigDecimal(300), resultCloth.getPrice());
    }
}