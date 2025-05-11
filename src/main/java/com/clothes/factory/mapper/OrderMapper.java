package com.clothes.factory.mapper;

import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final ClothMapper clothMapper;

    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalOrderPrice(order.getTotalOrderPrice())
                .paid(order.isPaid())
                .sent(order.isSent())
                .shipmentMethod(order.getShipmentMethod())
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

    public List<Long> mapToOrdersIdsFromOrders(final List<Order> orders) {
        return orders.stream()
                .map(Order::getId)
                .toList();
    }

}
