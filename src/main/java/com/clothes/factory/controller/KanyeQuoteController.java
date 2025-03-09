package com.clothes.factory.controller;

import com.clothes.factory.domain.KanyeQuoteDto;
import com.clothes.factory.exception.api.QuoteNotFoundException;
import com.clothes.factory.service.KanyeQuoteService;
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
