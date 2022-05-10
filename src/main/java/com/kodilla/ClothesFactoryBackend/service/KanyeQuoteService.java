package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.client.kanye_west_quotes_api.KanyeWestApiClient;
import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class KanyeQuoteService {
    private final KanyeWestApiClient kanyeWestApiClient;

    public String getQuote() throws QuoteNotFoundException {
        KanyeQuoteDto kanyeQuoteDto = kanyeWestApiClient.getQuote();
        return kanyeQuoteDto.getQuote();
    }
}
