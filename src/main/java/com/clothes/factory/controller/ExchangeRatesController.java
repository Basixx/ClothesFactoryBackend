package com.clothes.factory.controller;

import com.clothes.factory.domain.ExchangeRateDto;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.facade.ExchangeRateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeRatesController {

    private final ExchangeRateFacade exchangeRateFacade;

    @GetMapping
    @ResponseStatus(OK)
    public ExchangeRateDto getExchangeRate(@RequestParam String from, @RequestParam String to) throws CurrencyExchangeFailedException {
        return exchangeRateFacade.getExchange(from, to);
    }

}
