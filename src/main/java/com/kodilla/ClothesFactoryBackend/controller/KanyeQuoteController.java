package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.exception.QuoteNotFoundException;
import com.kodilla.ClothesFactoryBackend.facade.KanyeQuoteFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kanye")
@RequiredArgsConstructor
public class KanyeQuoteController {

    private final KanyeQuoteFacade kanyeQuoteFacade;

    @GetMapping
    public ResponseEntity<String> getQuote() throws QuoteNotFoundException {
        return ResponseEntity.ok(kanyeQuoteFacade.getQuote());
    }
}
