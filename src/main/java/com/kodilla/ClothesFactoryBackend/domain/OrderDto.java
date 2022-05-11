package com.kodilla.ClothesFactoryBackend.domain;

import com.kodilla.ClothesFactoryBackend.auxiliary.ShipmentMethod;
import lombok.*;
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
    private String address;
    private Long userId;
    private List<Long> clothesIdList;
}