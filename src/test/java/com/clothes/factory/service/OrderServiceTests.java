package com.clothes.factory.service;

import com.clothes.factory.auxiliary.shipment.ShipmentConfigService;
import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cart.EmptyCartException;
import com.clothes.factory.exception.order.OrderAlreadyPaidException;
import com.clothes.factory.exception.order.OrderAlreadySentException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.order.OrderNotPaidException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.mail.AdminMailCreator;
import com.clothes.factory.mail.UserMailCreator;
import com.clothes.factory.repository.CartRepository;
import com.clothes.factory.repository.OrderRepository;
import com.clothes.factory.repository.PaymentHistoryRepository;
import com.clothes.factory.repository.ShipmentHistoryRepository;
import com.clothes.factory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.clothes.factory.auxiliary.ShipmentMethod.DHL;
import static com.clothes.factory.auxiliary.ShipmentMethod.FEDEX;
import static com.clothes.factory.auxiliary.ShipmentMethod.IN_POST;
import static com.clothes.factory.auxiliary.ShipmentMethod.UPS;
import static com.clothes.factory.object_mother.CartMother.createCart;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.OrderMother.createOrder;
import static com.clothes.factory.object_mother.UserMother.createUser1;
import static com.clothes.factory.object_mother.UserMother.createUser2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ShipmentConfigService shipmentConfigService;

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


    @Test
    void testGetAllOrders() {
        //Given
        User user1 = createUser1();
        Order order1 = createOrder(5L, user1, new BigDecimal(100), IN_POST);

        User user2 = createUser2();
        Order order2 = createOrder(6L, user2, new BigDecimal(200), DHL);

        List<Order> orderList = List.of(order1, order2);
        when(orderRepository.findAll(0, 10))
                .thenReturn(orderList);

        //When
        List<Order> resultList = orderService.getAllOrders(0, 10);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(order1));
        assertTrue(resultList.contains(order2));
    }

    @Test
    void testGetAllUsersOrder() {
        //Given
        User user = createUser1();
        Order order1 = createOrder(5L, user, new BigDecimal(100), UPS);
        Order order2 = createOrder(6L, user, new BigDecimal(200), FEDEX);
        List<Order> orderList = List.of(order1, order2);

        when(orderRepository.findAllByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(orderList);

        //When
        List<Order> resultList = orderService.getAllUsersOrder(6L, 0, 10);

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(order1));
        assertTrue(resultList.contains(order2));
    }

    @Test
    void testCreateOrder() throws UserNotFoundException, EmptyCartException, CartNotFoundException, CurrencyExchangeFailedException {
        //Given
        User user = createUser1();
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth = createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        when(shipmentConfigService.getShippingPrice(any())).thenReturn(new BigDecimal(10));
        when(shipmentConfigService.getDeliveryDays(any())).thenReturn(3);
        Order order = createOrder(9L, user, new BigDecimal(500), FEDEX);
        order.setClothesList(List.of(cloth));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(cartRepository.findById(anyLong()))
                .thenReturn(Optional.of(cart));
        when(orderRepository.save(any()))
                .thenReturn(order);
        doNothing().when(emailService)
                .send(any());

        //When
        Order resultOrder = orderService.createOrder(6L, UPS);

        //Then
        assertEquals(FEDEX, resultOrder.getShipmentMethod());
        assertEquals(user, resultOrder.getUser());
        assertEquals(1, resultOrder.getClothesList().size());
    }

    @Test
    void testSetOrderToPaid() throws OrderNotFoundException, OrderAlreadyPaidException {
        User user = createUser1();
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth = createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        Order order = createOrder(9L, user, new BigDecimal(500), FEDEX);
        order.setClothesList(List.of(cloth));
        order.setPaid(false);

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));
        doNothing().when(emailService)
                .send(any());

        //When
        Order resultOrder = orderService.setOrderPaid(6L);

        //Then
        assertTrue(resultOrder.isPaid());
    }

    @Test
    void testSetOrderToSent() throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        User user = createUser1();
        Cart cart = createCart(new BigDecimal(100));
        Cloth cloth = createCloth1();
        cart.setClothesList(List.of(cloth));
        user.setCart(cart);
        Order order = createOrder(9L, user, new BigDecimal(500), FEDEX);
        order.setClothesList(List.of(cloth));

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));
        doNothing().when(emailService)
                .send(any());

        //When
        Order resultOrder = orderService.setOrderSent(6L);

        //Then
        assertTrue(resultOrder.isSent());
    }

}
