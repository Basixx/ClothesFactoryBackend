package com.kodilla.ClothesFactoryBackend.domain;

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
    private String shipmentCompanyName;
    private BigDecimal shippingPrice;
    private int deliveryDays;
    private String address;
    private Long userId;
    private String userMail;
    private List<Long> clothesIdList;
}