package com.clothes.factory.client.quotes_api;

import com.clothes.factory.domain.QuoteDto;
import com.clothes.factory.exception.api.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuoteApiClient {

    private final RestTemplate restTemplate;

    @Value("${kanye.west.api.endpoint}")
    private String quoteEndpoint;

    public QuoteDto getQuote() throws QuoteNotFoundException {
        URI url = UriComponentsBuilder.fromUriString(quoteEndpoint)
                .build()
                .encode()
                .toUri();
        QuoteDto quoteResponse = restTemplate.getForObject(url, QuoteDto.class);
        return Optional.ofNullable(quoteResponse).orElseThrow(QuoteNotFoundException::new);
    }

}
