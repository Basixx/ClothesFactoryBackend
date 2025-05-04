package com.clothes.factory.client.exchange_rates_api;

import com.clothes.factory.domain.ExchangeRatesClientDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
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
    private final TomcatServletWebServerFactory tomcatServletWebServerFactory;

    @Value("${exchange.rates.api.endpoint}")
    private String exchangeRateEndpoint;

    @Value("${api.layer.key}")
    private String exchangeRateKey;

    public ExchangeRatesClientDto getConversion(String to,
                                                String from,
                                                BigDecimal amount) throws CurrencyExchangeFailedException {
        ExchangeRatesClientDto rateResponse = restTemplate.getForObject(
                exchangeRateURL(
                        to,
                        from,
                        amount
                ),
                ExchangeRatesClientDto.class
        );
        return Optional.ofNullable(rateResponse).orElseThrow(CurrencyExchangeFailedException::new);
    }

    private URI exchangeRateURL(String to, String from, BigDecimal amount) {
        return exchangeRateUri()
                .queryParam("to", to)
                .queryParam("from", from)
                .queryParam("amount", amount)
                .build()
                .encode()
                .toUri();
    }

    private UriComponentsBuilder exchangeRateUri() {
        return UriComponentsBuilder.fromUriString(exchangeRateEndpoint)
                .queryParam("apikey", exchangeRateKey);
    }

}
