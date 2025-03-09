package com.clothes.factory.service;

import com.clothes.factory.client.exchange_rates_api.ExchangeRatesApiClient;
import com.clothes.factory.domain.ExchangeRate;
import com.clothes.factory.domain.ExchangeRatesClientDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.repository.ExchangeRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRatesApiClient exchangeRatesApiClient;

    public ExchangeRate getExchangeRate(String from, String to) throws CurrencyExchangeFailedException {
        if (exchangeRatesRepository.existsByFromCurrencyAndToCurrency(from, to)) {
            return exchangeRatesRepository.findByFromCurrencyAndToCurrency(from, to);
        } else {
            ExchangeRatesClientDto exchangeRatesClientDto = exchangeRatesApiClient.getConversion(from, to, BigDecimal.ONE);
            ExchangeRate exchangeRate = ExchangeRate
                    .builder()
                    .fromCurrency(from)
                    .toCurrency(to)
                    .currencyRate(exchangeRatesClientDto.getResult())
                    .build();
            return exchangeRatesRepository.save(exchangeRate);
        }
    }

    public void deleteAllRates() {
        exchangeRatesRepository.deleteAll();
    }

}
