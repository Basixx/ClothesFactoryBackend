package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.auxiliary.Prices;
import com.kodilla.ClothesFactoryBackend.auxiliary.Shipment;
import com.kodilla.ClothesFactoryBackend.domain.*;
import com.kodilla.ClothesFactoryBackend.exception.*;
import com.kodilla.ClothesFactoryBackend.mail.MailCreator;
import com.kodilla.ClothesFactoryBackend.repository.CartRepository;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final EmailService emailService;
    private final MailCreator mailCreator;

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

    public Order createOrder(final Long userId, final Shipment shipment) throws UserNotFoundException, CartNotFoundException, EmptyCartException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cartFromDb = cartRepository.findById(userFromDb.getCart().getId()).orElseThrow(CartNotFoundException::new);
        BigDecimal shippingPrice = Prices.findShippingPrice(shipment);
        String address = userFromDb.getStreet() + ", " + userFromDb.getStreetAndApartmentNumber() + ", " + userFromDb.getCity() + ", " + userFromDb.getPostCode();

        if(cartFromDb.getClothesList().size() == 0) {
            throw new EmptyCartException();
        } else {
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .paid(false)
                    .sent(false)
                    .user(userFromDb)
                    .shipment(shipment)
                    .shippingPrice(shippingPrice)
                    .totalOrderPrice(cartFromDb.getTotalPrice().add(shippingPrice))
                    .address(address)
                    .clothesList(cartFromDb.getClothesList())
                    .build();


            for(Cloth cloth : cartFromDb.getClothesList()){
                cloth.setCart(null);
                cloth.setOrder(order);
            }
            cartFromDb.setTotalPrice(BigDecimal.ZERO);
            cartFromDb.setClothesList(new ArrayList<>());
            System.out.println(cartFromDb.getClothesList().size());

            Order savedOrder = orderRepository.save(order);
            emailService.send(mailCreator.createMailForAdmin(savedOrder));
            emailService.send(mailCreator.createMailForUser(savedOrder));

            return savedOrder;
        }
    }

    public Order setOrderPaid(final Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        Order orderFromDb = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (orderFromDb.isPaid()) {
            throw new OrderAlreadyPaidException();
        }
        orderFromDb.setPaid(true);
        emailService.send(mailCreator.createMailOrderPaid(orderFromDb));
        return orderFromDb;
    }

    public Order setOrderSent(final Long id) throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        Order orderFromDb = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (!orderFromDb.isPaid()) {
            throw new OrderNotPaidException();
        } else if (orderFromDb.isSent()) {
            throw new OrderAlreadySentException();
        }
        orderFromDb.setSent(true);
        emailService.send(mailCreator.createMailOrderSent(orderFromDb));
        return orderFromDb;
    }
}