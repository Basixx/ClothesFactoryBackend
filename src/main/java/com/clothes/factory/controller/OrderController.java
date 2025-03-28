package com.clothes.factory.controller;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentMethod;
import com.clothes.factory.domain.OrderDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cart.EmptyCartException;
import com.clothes.factory.exception.order.OrderAlreadyPaidException;
import com.clothes.factory.exception.order.OrderAlreadySentException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.order.OrderNotPaidException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderFacade.getOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderFacade.getAllOrders());
    }

    @GetMapping(value = "/byUser/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(orderFacade.getOrdersByUser(userId));
    }

    @PostMapping(value = "/{userId}/{shipmentMethod}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @PathVariable ShipmentMethod shipmentMethod) throws UserNotFoundException, CartNotFoundException, EmptyCartException, CurrencyExchangeFailedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderFacade.createOrder(userId, shipmentMethod));
    }

    @PutMapping(value = "/paid/{id}")
    public ResponseEntity<OrderDto> setOrderToPaid(@PathVariable Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        return ResponseEntity.ok(orderFacade.setOrderToPaid(id));
    }

    @PutMapping(value = "/sent/{id}")
    public ResponseEntity<OrderDto> setOrderToSent(@PathVariable Long id) throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        return ResponseEntity.ok(orderFacade.setOrderToSent(id));
    }

}
