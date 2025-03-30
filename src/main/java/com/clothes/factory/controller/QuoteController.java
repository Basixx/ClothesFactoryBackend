package com.clothes.factory.controller;

import com.clothes.factory.domain.QuoteDto;
import com.clothes.factory.exception.api.QuoteNotFoundException;
import com.clothes.factory.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping
    @ResponseStatus(OK)
    public QuoteDto getQuote() throws QuoteNotFoundException {
        return quoteService.getQuote();
    }

}
