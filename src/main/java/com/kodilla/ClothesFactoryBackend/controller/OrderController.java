package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderPaidException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.OrderMapper;
import com.kodilla.ClothesFactoryBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.getOrder(id)));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.mapToOrderDtoList(orderService.getAllOrders()));
    }

    @GetMapping(value = "/byUser/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser (@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDtoList(orderService.getAllUsersOrder(userId)));
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.createOrder(userId)));
    }

    @PutMapping(value = "/paid/{id}")
    public ResponseEntity<OrderDto> setOrderToPaid (@PathVariable Long id) throws OrderNotFoundException, OrderPaidException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.setOrderPaid(id)));
    }

    @PutMapping(value = "/sent/{id}")
    public ResponseEntity<OrderDto> setOrderToSent (@PathVariable Long id) throws OrderNotFoundException, OrderPaidException {
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderService.setOrderSent(id)));
    }
}