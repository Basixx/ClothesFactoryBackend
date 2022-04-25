package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderPaidException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.OrderMapper;
import com.kodilla.ClothesFactoryBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderDto getOrder(Long id) throws OrderNotFoundException {
        return orderMapper.mapToOrderDto(orderService.getOrder(id));
    }

    public List<OrderDto> getAllOrders() {
        return orderMapper.mapToOrderDtoList(orderService.getAllOrders());
    }

    public List<OrderDto> getOrdersByUser(Long userId) throws UserNotFoundException {
        return orderMapper.mapToOrderDtoList(orderService.getAllUsersOrder(userId));
    }

    public OrderDto createOrder(Long userId) throws UserNotFoundException, CartNotFoundException {
        return orderMapper.mapToOrderDto(orderService.createOrder(userId));
    }

    public OrderDto setOrderToPaid(Long id) throws OrderNotFoundException, OrderPaidException {
        return orderMapper.mapToOrderDto(orderService.setOrderPaid(id));
    }

    public OrderDto setOrderToSent(Long id) throws OrderNotFoundException, OrderPaidException {
        return orderMapper.mapToOrderDto(orderService.setOrderSent(id));
    }
}