package com.kodilla.ClothesFactoryBackend.client.exchange_rates_api;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRatesClientDto;
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
public class ExchangeRatesClient {

    private final RestTemplate restTemplate;

    @Value("${exchange.rates.api.endpoint:defaultValue}")
    private String exchangeRateEndpoint;

    @Value("${exchange.rates.api.key:defaultValue}")
    private String getExchangeRateKey;

    public ExchangeRatesClientDto getConversion(String to, String from, BigDecimal amount) {
        URI url = UriComponentsBuilder.fromHttpUrl(
                exchangeRateEndpoint)
                .queryParam("to", to)
                .queryParam("from", from)
                .queryParam("amount", amount)
                .queryParam("apikey", getExchangeRateKey)
                .build()
                .encode()
                .toUri();

        ExchangeRatesClientDto rateResponse = restTemplate.getForObject(
                url,
                ExchangeRatesClientDto.class
        );
        return Optional.ofNullable(rateResponse).orElse(new ExchangeRatesClientDto());
    }
}
