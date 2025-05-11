package com.clothes.factory.facade;

import com.clothes.factory.auxiliary.ShipmentMethod;
import com.clothes.factory.domain.OrderDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cart.EmptyCartException;
import com.clothes.factory.exception.order.OrderAlreadyPaidException;
import com.clothes.factory.exception.order.OrderAlreadySentException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.order.OrderNotPaidException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.mapper.OrderMapper;
import com.clothes.factory.service.OrderService;
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

    public List<OrderDto> getAllOrders(int page, int size) {
        return orderMapper.mapToOrderDtoList(orderService.getAllOrders(page, size));
    }

    public List<OrderDto> getOrdersByUser(Long userId,
                                          int page,
                                          int size) {
        return orderMapper.mapToOrderDtoList(orderService.getAllUsersOrder(userId, page, size));
    }

    public OrderDto createOrder(Long userId, ShipmentMethod shipmentCompany)
            throws UserNotFoundException,
            CartNotFoundException,
            EmptyCartException,
            CurrencyExchangeFailedException {
        return orderMapper.mapToOrderDto(orderService.createOrder(userId, shipmentCompany));
    }

    public OrderDto setOrderToPaid(Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        return orderMapper.mapToOrderDto(orderService.setOrderPaid(id));
    }

    public OrderDto setOrderToSent(Long id)
            throws OrderNotFoundException,
            OrderNotPaidException,
            OrderAlreadySentException {
        return orderMapper.mapToOrderDto(orderService.setOrderSent(id));
    }

}
