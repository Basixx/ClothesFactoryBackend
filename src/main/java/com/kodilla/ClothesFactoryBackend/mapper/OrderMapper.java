package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.OrderDto;
import com.kodilla.ClothesFactoryBackend.exception.ClothNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.OrderNotFoundException;
import com.kodilla.ClothesFactoryBackend.exception.UserNotFoundException;
import com.kodilla.ClothesFactoryBackend.repository.OrderRepository;
import com.kodilla.ClothesFactoryBackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderMapper {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ClothMapper clothMapper;

    public Order mapToOrder(final OrderDto orderDto) throws UserNotFoundException, ClothNotFoundException {
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .totalOrderPrice(orderDto.getTotalOrderPrice())
                .paid(orderDto.isPaid())
                .sent(orderDto.isSent())
                .user(orderDto.getUserId() == null ? null : userRepository.findById(orderDto.getUserId()).orElseThrow(UserNotFoundException::new))
                .clothesList(orderDto.getClothesIdList() == null ? null : clothMapper.mapToClothesFromIds(orderDto.getClothesIdList()))
                .build();
    }

    public OrderDto mapToOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalOrderPrice(order.getTotalOrderPrice())
                .paid(order.isPaid())
                .sent(order.isSent())
                .userId(order.getUser().getId())
                .clothesIdList(clothMapper.mapToClothesIdsFromClothes(order.getClothesList()))
                .build();
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public List<Order> mapToOrdersFromIds(final List<Long> ordersIds) throws OrderNotFoundException {
        List<Order> orders = new ArrayList<>();

        if(ordersIds.size() > 0) {
            for (Long orderId : ordersIds){
                orders.add(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));
            }
        }
        return orders;
    }

    public List<Long> mapToOrdersIdsFromOrders(final List<Order> orders) {
        List<Long> ordersIds = new ArrayList<>();
        if (orders.size() > 0) {
            ordersIds = orders.stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());
        }
        return ordersIds;
    }
}