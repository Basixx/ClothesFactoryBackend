package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentMethod;
import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.exception.cart.CartNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.cart.EmptyCartException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadyPaidException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderAlreadySentException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.order.OrderNotPaidException;
import com.kodilla.ClothesFactoryBackend.exception.user.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.mapper.OrderMapper;
import com.kodilla.ClothesFactoryBackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
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

    public OrderDto createOrder(Long userId, ShipmentMethod shipmentCompany) throws UserNotFoundException, CartNotFoundException, EmptyCartException, CurrencyExchangeFailedException {
        return orderMapper.mapToOrderDto(orderService.createOrder(userId, shipmentCompany));
    }

    public OrderDto setOrderToPaid(Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        return orderMapper.mapToOrderDto(orderService.setOrderPaid(id));
    }

    public OrderDto setOrderToSent(Long id) throws OrderNotFoundException, OrderNotPaidException, OrderAlreadySentException {
        return orderMapper.mapToOrderDto(orderService.setOrderSent(id));
    }
}