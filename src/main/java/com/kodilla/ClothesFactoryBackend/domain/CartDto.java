package com.kodilla.ClothesFactoryBackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
