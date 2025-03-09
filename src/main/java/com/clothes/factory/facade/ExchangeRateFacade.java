package com.clothes.factory.facade;

import com.clothes.factory.domain.ExchangeRateDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.mapper.ExchangeRatesMapper;
import com.clothes.factory.service.ExchangeRatesService;
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
