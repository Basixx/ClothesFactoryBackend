package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.facade.ExchangeRateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangeRatesController {

    private final ExchangeRateFacade exchangeRateFacade;

    @GetMapping
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@RequestParam String from, @RequestParam String to) throws CurrencyExchangeFailedException {
        return ResponseEntity.ok(exchangeRateFacade.getExchange(from, to));
    }

}
