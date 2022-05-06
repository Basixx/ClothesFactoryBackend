package com.kodilla.ClothesFactoryBackend.client.exchange_rates_api;

import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public void getExchangeRate(@RequestParam String to, @RequestParam String from, @RequestParam BigDecimal amount) {
        ExchangeRatesDto exchangeRatesDto = exchangeRatesClient.getConversion(
                to,
                from,
                amount
        );
        System.out.println(exchangeRatesDto.getResult());
    }
}
