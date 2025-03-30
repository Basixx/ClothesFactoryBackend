package com.clothes.factory.service;

import com.clothes.factory.client.quotes_api.QuoteApiClient;
import com.clothes.factory.domain.QuoteDto;
import com.clothes.factory.exception.api.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteApiClient quoteApiClient;

    public QuoteDto getQuote() throws QuoteNotFoundException {
        return quoteApiClient.getQuote();
    }

}
