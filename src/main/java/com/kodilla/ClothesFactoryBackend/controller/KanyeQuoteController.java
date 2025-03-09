package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.KanyeQuoteDto;
import com.kodilla.ClothesFactoryBackend.exception.api.QuoteNotFoundException;
import com.kodilla.ClothesFactoryBackend.service.KanyeQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kanye")
@RequiredArgsConstructor
public class KanyeQuoteController {

    private final KanyeQuoteService kanyeQuoteService;

    @GetMapping
    public ResponseEntity<KanyeQuoteDto> getQuote() throws QuoteNotFoundException {
        return ResponseEntity.ok(kanyeQuoteService.getQuote());
    }

}
