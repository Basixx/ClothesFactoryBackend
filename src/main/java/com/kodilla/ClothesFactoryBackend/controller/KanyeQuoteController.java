package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.client.kanye_west_quotes_api.KanyeWestApiClient;
import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kanye")
@RequiredArgsConstructor
public class KanyeQuoteController {

    private final KanyeWestApiClient kanyeWestApiClient;

    @GetMapping
    public ResponseEntity<String> getQuote() throws QuoteNotFoundException {
        KanyeQuoteDto kanyeQuoteDto = kanyeWestApiClient.getQuote();
        return ResponseEntity.ok(kanyeQuoteDto.getQuote());
    }
}
