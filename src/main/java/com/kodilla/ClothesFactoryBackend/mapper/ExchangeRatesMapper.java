package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRate;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesMapper {

    public ExchangeRate mapToExchangeRate(final ExchangeRateDto exchangeRateDto) {
        return ExchangeRate.builder()
                .fromCurrency(exchangeRateDto.getFromCurrency())
                .toCurrency(exchangeRateDto.getToCurrency())
                .currencyRate(exchangeRateDto.getCurrencyRate())
                .build();
    }

    public ExchangeRateDto mapToExchangeRateDto(final ExchangeRate exchangeRate) {
        return ExchangeRateDto.builder()
                .id(exchangeRate.getId())
                .fromCurrency(exchangeRate.getFromCurrency())
                .toCurrency(exchangeRate.getToCurrency())
                .currencyRate(exchangeRate.getCurrencyRate())
                .build();
    }
}
