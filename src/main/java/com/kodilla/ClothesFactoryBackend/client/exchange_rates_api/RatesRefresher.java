package com.kodilla.ClothesFactoryBackend.client.exchange_rates_api;

import com.kodilla.ClothesFactoryBackend.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatesRefresher {
    private final ExchangeRatesService exchangeRatesService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRates() {
        exchangeRatesService.deleteAllRates();
    }
}
