package com.clothes.factory.mapper;

import com.clothes.factory.domain.ExchangeRate;
import com.clothes.factory.domain.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRatesMapper {

    public ExchangeRateDto mapToExchangeRateDto(final ExchangeRate exchangeRate) {
        return ExchangeRateDto.builder()
                .id(exchangeRate.getId())
                .fromCurrency(exchangeRate.getFromCurrency())
                .toCurrency(exchangeRate.getToCurrency())
                .currencyRate(exchangeRate.getCurrencyRate())
                .build();
    }

}
