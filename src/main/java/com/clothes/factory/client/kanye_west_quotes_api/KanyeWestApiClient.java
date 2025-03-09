package com.clothes.factory.client.kanye_west_quotes_api;

import com.clothes.factory.domain.KanyeQuoteDto;
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
public class KanyeWestApiClient {

    private final RestTemplate restTemplate;

    @Value("${kanye.west.api.endpoint:defaultValue}")
    private String kanyeQuoteEndpoint;

    public KanyeQuoteDto getQuote() throws QuoteNotFoundException {
        URI url = UriComponentsBuilder.fromUriString(kanyeQuoteEndpoint)
                .build()
                .encode()
                .toUri();
        KanyeQuoteDto quoteResponse = restTemplate.getForObject(url, KanyeQuoteDto.class);
        return Optional.ofNullable(quoteResponse).orElseThrow(QuoteNotFoundException::new);
    }

}
