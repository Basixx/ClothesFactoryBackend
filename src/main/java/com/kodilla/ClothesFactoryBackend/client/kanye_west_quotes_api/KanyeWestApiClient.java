package com.kodilla.ClothesFactoryBackend.client.kanye_west_quotes_api;

import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KanyeWestApiClient {
    private final RestTemplate restTemplate;

    @Value("${kanye.west.api.endpoint:defaultValue}")
    private String exchangeRateEndpoint;

    public KanyeQuoteDto getQuote() throws QuoteNotFoundException {
        URI url = UriComponentsBuilder.fromHttpUrl(exchangeRateEndpoint)
                .build()
                .encode()
                .toUri();
        KanyeQuoteDto quoteResponse = restTemplate.getForObject(url, KanyeQuoteDto.class);
        return Optional.ofNullable(quoteResponse).orElseThrow(QuoteNotFoundException::new);
    }
}