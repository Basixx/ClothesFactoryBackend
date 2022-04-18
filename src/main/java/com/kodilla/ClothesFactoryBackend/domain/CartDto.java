package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private BigDecimal totalPrice;
    private List<Long> clothesIdList;
}