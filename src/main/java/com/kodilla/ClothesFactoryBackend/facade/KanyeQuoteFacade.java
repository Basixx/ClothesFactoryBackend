package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import com.kodilla.ClothesFactoryBackend.service.KanyeQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KanyeQuoteFacade {
    private final KanyeQuoteService kanyeQuoteService;

    public String getQuote() throws QuoteNotFoundException {
        return kanyeQuoteService.getQuote();
    }
}
