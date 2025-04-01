package com.clothes.factory.service;

import com.clothes.factory.auxiliary.shipment.strategy.CompanySetter;
import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.auxiliary.shipment.strategy.ShipmentMethod;
import com.clothes.factory.domain.Cart;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.PaymentHistory;
import com.clothes.factory.domain.ShipmentHistory;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ShipmentHistoryRepository shipmentHistoryRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final EmailService emailService;
    private final UserMailCreator userMailCreator;
    private final AdminMailCreator adminMailCreator;
    private final CompanySetter companySetter;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllUsersOrder(final Long userId) throws UserNotFoundException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userFromDb.getOrdersList();
    }

    public Order getOrder(final Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Order createOrder(final Long userId, final ShipmentMethod company) throws UserNotFoundException, CartNotFoundException, EmptyCartException, CurrencyExchangeFailedException {
        User userFromDb = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Cart cartFromDb = cartRepository.findById(userFromDb.getCart().getId()).orElseThrow(CartNotFoundException::new);

        ShipmentCompany shipmentCompany = companySetter.setCompany(company);

        String address = userFromDb.getStreet() + ", " + userFromDb.getStreetAndApartmentNumber() + ", " + userFromDb.getCity() + ", " + userFromDb.getPostCode();

        if (cartFromDb.getClothesList().isEmpty()) {
            throw new EmptyCartException();
        } else {
            Order order = Order.builder()
                    .orderDate(LocalDate.now())
                    .paid(false)
                    .sent(false)
                    .user(userFromDb)
                    .shipmentCompany(shipmentCompany)
                    .shipmentCompanyName(shipmentCompany.getName())
                    .shippingPrice(shipmentCompany.getPrice())
                    .deliveryDays(shipmentCompany.getDeliveryDays())
                    .totalOrderPrice(cartFromDb.getTotalPrice().add(shipmentCompany.getPrice()))
                    .address(address)
                    .clothesList(cartFromDb.getClothesList())
                    .build();

            for (Cloth cloth : cartFromDb.getClothesList()) {
                cloth.setCart(null);
                cloth.setOrder(order);
            }
            cartFromDb.setTotalPrice(BigDecimal.ZERO);
            cartFromDb.setClothesList(new ArrayList<>());

            Order savedOrder = orderRepository.save(order);
            emailService.send(adminMailCreator.createMailForAdminOrderCreated(savedOrder));
            emailService.send(userMailCreator.createMailForUserOrderCreated(savedOrder));

            return savedOrder;
        }
    }

    public Order setOrderPaid(final Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        Order orderFromDb = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        if (orderFromDb.isPaid()) {
            throw new OrderAlreadyPaidException();
        }
        orderFromDb.setPaid(true);
        emailService.send(userMailCreator.createMailOrderPaid(orderFromDb));
        paymentHistoryRepository.save(PaymentHistory.builder()
                .paymentTime(LocalDateTime.now())
                .orderId(orderFromDb.getId())
                .userMail(orderFromDb.getUser().getEmailAddress())
                .price(orderFromDb.getTotalOrderPrice())
                .build());
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
        emailService.send(userMailCreator.createMailOrderSent(orderFromDb));
        shipmentHistoryRepository.save(ShipmentHistory.builder()
                .shipmentTime(LocalDateTime.now())
                .orderId(orderFromDb.getId())
                .userMail(orderFromDb.getUser().getEmailAddress())
                .shippingCompany(orderFromDb.getShipmentCompanyName())
                .build());
        return orderFromDb;
    }

}
