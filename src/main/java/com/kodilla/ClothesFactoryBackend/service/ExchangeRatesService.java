package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.exchange_rates_api.ExchangeRatesClient;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRate;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRatesClientDto;
import com.kodilla.ClothesFactoryBackend.repository.ExchangeRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service
@RequiredArgsConstructor
public class ExchangeRatesService {
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRatesClient exchangeRatesClient;

    public ExchangeRate getExchangeRate(String from, String to) {
        if(exchangeRatesRepository.existsByFromCurrencyAndToCurrency(from, to)) {
            return exchangeRatesRepository.findByFromCurrencyAndToCurrency(from, to);
        } else {
           ExchangeRatesClientDto exchangeRatesClientDto = exchangeRatesClient.getConversion(from, to, BigDecimal.ONE);
           ExchangeRate exchangeRate = ExchangeRate
                   .builder()
                   .fromCurrency(from)
                   .toCurrency(to)
                   .currencyRate(exchangeRatesClientDto.getResult())
                   .build();
            return exchangeRatesRepository.save(exchangeRate);
        }
    }
}
