package com.clothes.factory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal currencyRate;

}
