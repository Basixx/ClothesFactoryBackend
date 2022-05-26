package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.mapper.ExchangeRatesMapper;
import com.kodilla.ClothesFactoryBackend.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateFacade {
    private final ExchangeRatesMapper exchangeRatesMapper;
    private final ExchangeRatesService exchangeRatesService;

    public ExchangeRateDto getExchange(String from, String to) throws CurrencyExchangeFailedException {
        return exchangeRatesMapper.mapToExchangeRateDto(exchangeRatesService.getExchangeRate(from, to));
    }
}