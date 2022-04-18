package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothDto {
    private Long id;
    private String type;
    private String color;
    private String print;
    private String font;
    private String printColor;
    private int quantity;
    private BigDecimal price;
    private Long cartId;
    private Long orderId;
}