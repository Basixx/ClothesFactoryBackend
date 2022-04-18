package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.Cart;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderPaidException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllUsersOrder (final Long userId) throws UserNotFoundException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userFromDb.getOrdersList();
    }

    public Order getOrder (final Long id) throws OrderNotFoundException{
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Order createOrder(final Long userId) throws UserNotFoundException{
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cartFromDb = userFromDb.getCart();
        BigDecimal shipping = new BigDecimal(10);
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .paid(false)
                .sent(false)
                .user(userFromDb)
                .totalOrderPrice(cartFromDb.getTotalPrice().add(shipping))
                .clothesList(cartFromDb.getClothesList())
                .build();
        cartFromDb.setClothesList(new ArrayList<>());
        cartRepository.save(cartFromDb);
        orderRepository.save(order);
        return order;
    }

    public Order setOrderPaid(final Long id) throws OrderNotFoundException, OrderPaidException {
        Order orderFromDb = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (orderFromDb.isPaid()) {
            throw new OrderPaidException();
        }
        orderFromDb.setPaid(true);
        orderRepository.save(orderFromDb);
        return orderFromDb;
    }

    public Order setOrderSent(final Long id) throws OrderNotFoundException, OrderPaidException {
        Order orderFromDb = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (!orderFromDb.isPaid()) {
            throw new OrderPaidException();
        }
        orderFromDb.setSent(true);
        orderRepository.save(orderFromDb);
        return orderFromDb;
    }
}