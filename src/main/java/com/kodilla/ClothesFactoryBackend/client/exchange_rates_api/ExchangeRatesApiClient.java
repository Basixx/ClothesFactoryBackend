package com.kodilla.ClothesFactoryBackend.client.exchange_rates_api;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRatesClientDto;
import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExchangeRatesApiClient {

    private final RestTemplate restTemplate;

    @Value("${exchange.rates.api.endpoint:defaultValue}")
    private String exchangeRateEndpoint;

    @Value("${api.layer.key:defaultValue}")
    private String exchangeRateKey;

    public ExchangeRatesClientDto getConversion(String to, String from, BigDecimal amount) throws CurrencyExchangeFailedException {
        URI url = UriComponentsBuilder.fromHttpUrl(
                exchangeRateEndpoint)
                .queryParam("to", to)
                .queryParam("from", from)
                .queryParam("amount", amount)
                .queryParam("apikey", exchangeRateKey)
                .build()
                .encode()
                .toUri();

        ExchangeRatesClientDto rateResponse = restTemplate.getForObject(
                url,
                ExchangeRatesClientDto.class
        );
        return Optional.ofNullable(rateResponse).orElseThrow(CurrencyExchangeFailedException::new);
    }
}
