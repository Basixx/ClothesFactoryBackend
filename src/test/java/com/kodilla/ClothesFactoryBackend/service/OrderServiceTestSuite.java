package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.CompanySetter;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentMethod;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Dhl;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Fedex;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.InPost;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Ups;
import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.cart.EmptyCartException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadyPaidException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadySentException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotPaidException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mail.AdminMailCreator;
import com.kodilla.ClothesFactoryBackend.mail.UserMailCreator;
import com.kodilla.ClothesFactoryBackend.object_mother.CartMother;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import com.kodilla.ClothesFactoryBackend.object_mother.OrderMother;
import com.kodilla.ClothesFactoryBackend.object_mother.UserMother;
import com.kodilla.ClothesFactoryBackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTestSuite {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentHistoryRepository shipmentHistoryRepository;
    @Mock
    private PaymentHistoryRepository paymentHistoryRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private UserMailCreator userMailCreator;
    @Mock
    private AdminMailCreator adminMailCreator;
    @Mock
    private CompanySetter companySetter;

    @Test
    void testGetAllOrders() {
        //Given
        User user1 = UserMother.createUser1();
        Order order1 = OrderMother.createOrder(5L, user1, new BigDecimal(100), new InPost());

        User user2 = UserMother.createUser2();
        Order order2 = OrderMother.createOrder(6L, user2, new BigDecimal(200), new Dhl());

        List<Order> orderList = List.of(order1, order2);
        when(orderRepository.findAll()).thenReturn(orderList);

        //When
        List<Order> resultList = orderService.getAllOrders();

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(order1));
        assertTrue(resultList.contains(order2));
    }

    @Test
    void testGetAllUsersOrder() throws UserNotFoundException {
        //Given
        User user = UserMother.createUser1();
        Order order1 = OrderMother.createOrder(5L, user, new BigDecimal(100), new Ups());
        Order order2 = OrderMother.createOrder(6L, user, new BigDecimal(200), new Fedex());
        List<Order> orderList = List.of(order1, order2);
        user.setOrdersList(orderList);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //When
        List<Order> resultList = orderService.getAllUsersOrder(6L);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(order1));
        assertTrue(resultList.contains(order2));
    }

    @Test
    void testCreateOrder() throws UserNotFoundException, EmptyCartException, CartNotFoundException, CurrencyExchangeFailedException {
        //Given
        User user = UserMother.createUser1();
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth = ClothMother.createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        ShipmentCompany shipmentCompany = new Fedex();
        Order order = OrderMother.createOrder(9L, user, new BigDecimal(500), shipmentCompany);
        order.setClothesList(List.of(cloth));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        when(orderRepository.save(any())).thenReturn(order);
        when(companySetter.setCompany(any())).thenReturn(shipmentCompany);
        doNothing().when(emailService).send(any());

        //When
        Order resultOrder = orderService.createOrder(6L, ShipmentMethod.UPS);

        //Then
        assertEquals(shipmentCompany, resultOrder.getShipmentCompany());
        assertEquals(user, resultOrder.getUser());
        assertEquals(1, resultOrder.getClothesList().size());
    }

    @Test
    void testSetOrderToPaid() throws OrderNotFoundException, OrderAlreadyPaidException {
        User user = UserMother.createUser1();
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth = ClothMother.createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        ShipmentCompany shipmentCompany = new Fedex();
        Order order = OrderMother.createOrder(9L, user, new BigDecimal(500), shipmentCompany);
        order.setClothesList(List.of(cloth));
        order.setPaid(false);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(emailService).send(any());

        //When
        Order resultOrder = orderService.setOrderPaid(6L);

        //Then
        assertTrue(resultOrder.isPaid());
    }

    @Test
    void testSetOrderToSent() throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        User user = UserMother.createUser1();
        Cart cart = CartMother.createCart(new BigDecimal(100));
        Cloth cloth = ClothMother.createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        ShipmentCompany shipmentCompany = new Fedex();
        Order order = OrderMother.createOrder(9L, user, new BigDecimal(500), shipmentCompany);
        order.setClothesList(List.of(cloth));

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(emailService).send(any());

        //When
        Order resultOrder = orderService.setOrderSent(6L);

        //Then
        assertTrue(resultOrder.isSent());
    }
}