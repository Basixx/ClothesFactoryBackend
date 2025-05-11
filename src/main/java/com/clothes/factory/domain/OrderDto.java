package com.clothes.factory.domain;

import com.clothes.factory.auxiliary.ShipmentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalOrderPrice;
    private boolean paid;
    private boolean sent;
    private ShipmentMethod shipmentMethod;
    private BigDecimal shippingPrice;
    private int deliveryDays;
    private String address;
    private Long userId;
    private String userMail;
    private List<Long> clothesIdList;

}
