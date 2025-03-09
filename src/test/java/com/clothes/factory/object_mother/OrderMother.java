package com.clothes.factory.object_mother;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.OrderDto;
import com.clothes.factory.domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderMother {

    public static Order createOrder(Long id, User user, BigDecimal price, ShipmentCompany shipmentCompany) {

        String address = user.toString();

        return Order.builder()
                .id(id)
                .orderDate(LocalDate.of(2022, 4, 22))
                .totalOrderPrice(price)
                .paid(true)
                .sent(false)
                .shipmentCompany(shipmentCompany)
                .shippingPrice(new BigDecimal(20))
                .address(address)
                .user(user)
                .clothesList(new ArrayList<>())
                .build();
    }

    public static OrderDto createOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .orderDate(LocalDate.of(2022, 4, 26))
                .totalOrderPrice(new BigDecimal(100))
                .paid(false)
                .sent(false)
                .shipmentCompanyName("InPost")
                .shippingPrice(new BigDecimal(20))
                .deliveryDays(3)
                .address("Wilcza, 5/6, Warsaw, 02-234")
                .userId(2L)
                .clothesIdList(new ArrayList<>())
                .build();
    }

}
