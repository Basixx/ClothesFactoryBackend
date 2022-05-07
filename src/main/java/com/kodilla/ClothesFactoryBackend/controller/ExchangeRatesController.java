package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.client.exchange_rates_api.ExchangeRatesClient;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRatesClientDto;
import com.kodilla.ClothesFactoryBackend.facade.ExchangeRateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangeRatesController {

    private final ExchangeRatesClient exchangeRatesClient;

    private  final ExchangeRateFacade exchangeRateFacade;
    @GetMapping(value = "/simpleRate")
    public ResponseEntity<Void> getSimpleExchangeRate(@RequestParam java.lang.String to, @RequestParam java.lang.String from, @RequestParam BigDecimal amount) {
        ExchangeRatesClientDto exchangeRatesClientDto = exchangeRatesClient.getConversion(
                to,
                from,
                amount
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(exchangeRateFacade.getExchange(from, to));
    }
}
