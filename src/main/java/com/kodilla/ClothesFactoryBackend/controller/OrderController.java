package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderPaidException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId) throws UserNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(orderFacade.createOrder(userId));
    }

    @PutMapping(value = "/paid/{id}")
    public ResponseEntity<OrderDto> setOrderToPaid(@PathVariable Long id) throws OrderNotFoundException, OrderPaidException {
        return ResponseEntity.ok(orderFacade.setOrderToPaid(id));
    }

    @PutMapping(value = "/sent/{id}")
    public ResponseEntity<OrderDto> setOrderToSent(@PathVariable Long id) throws OrderNotFoundException, OrderPaidException {
        return ResponseEntity.ok(orderFacade.setOrderToSent(id));
    }
}