package com.clothes.factory.service;

import com.clothes.factory.client.kanye_west_quotes_api.KanyeWestApiClient;
import com.clothes.factory.domain.KanyeQuoteDto;
import com.clothes.factory.exception.api.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class KanyeQuoteService {

    private final KanyeWestApiClient kanyeWestApiClient;

    public KanyeQuoteDto getQuote() throws QuoteNotFoundException {
        return kanyeWestApiClient.getQuote();
    }

}
