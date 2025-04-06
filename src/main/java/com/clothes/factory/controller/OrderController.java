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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping(value = "/orders/{id}")
    @ResponseStatus(OK)
    public OrderDto getOrder(@PathVariable Long id) throws OrderNotFoundException {
        return orderFacade.getOrder(id);
    }

    @GetMapping("/orders")
    @ResponseStatus(OK)
    public List<OrderDto> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return orderFacade.getAllOrders(page, size);
    }

    @GetMapping(value = "/users/{userId}/orders")
    @ResponseStatus(OK)
    public List<OrderDto> getOrdersByUser(@PathVariable Long userId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return orderFacade.getOrdersByUser(
                userId,
                page,
                size
        );
    }

    @PostMapping(value = "/users/{userId}/order/{shipmentMethod}")
    @ResponseStatus(CREATED)
    public OrderDto createOrder(@PathVariable Long userId, @PathVariable ShipmentMethod shipmentMethod)
            throws UserNotFoundException,
            CartNotFoundException,
            EmptyCartException,
            CurrencyExchangeFailedException {
        return orderFacade.createOrder(userId, shipmentMethod);
    }

    @PutMapping(value = "/orders/{id}/paid")
    @ResponseStatus(OK)
    public OrderDto setOrderToPaid(@PathVariable Long id) throws OrderNotFoundException, OrderAlreadyPaidException {
        return orderFacade.setOrderToPaid(id);
    }

    @PutMapping(value = "/orders/{id}/sent")
    @ResponseStatus(OK)
    public OrderDto setOrderToSent(@PathVariable Long id)
            throws OrderNotFoundException,
            OrderNotPaidException,
            OrderAlreadySentException {
        return orderFacade.setOrderToSent(id);
    }

}
