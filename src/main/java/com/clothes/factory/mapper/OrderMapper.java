package com.clothes.factory.mapper;

import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.OrderDto;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderRepository orderRepository;
    private final ClothMapper clothMapper;

    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalOrderPrice(order.getTotalOrderPrice())
                .paid(order.isPaid())
                .sent(order.isSent())
                .shipmentCompanyName(order.getShipmentCompanyName())
                .shippingPrice(order.getShippingPrice())
                .deliveryDays(order.getDeliveryDays())
                .address(order.getAddress())
                .userId(order.getUser().getId())
                .userMail(order.getUser().getEmailAddress())
                .clothesIdList(clothMapper.mapToClothesIdsFromClothes(order.getClothesList()))
                .build();
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .toList();
    }

    public List<Order> mapToOrdersFromIds(final List<Long> ordersIds) throws OrderNotFoundException {
        List<Order> orders = new ArrayList<>();

        if (!ordersIds.isEmpty()) {
            for (Long orderId : ordersIds) {
                orders.add(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));
            }
        }
        return orders;
    }

    public List<Long> mapToOrdersIdsFromOrders(final List<Order> orders) {
        List<Long> ordersIds = new ArrayList<>();
        if (!orders.isEmpty()) {
            ordersIds = orders.stream()
                    .map(Order::getId)
                    .toList();
        }
        return ordersIds;
    }

}
