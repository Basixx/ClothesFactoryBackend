package com.kodilla.ClothesFactoryBackend.client.exchange_rates_api;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ExchangeRatesDto {
    private BigDecimal result;
}
