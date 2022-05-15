package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.ExchangeRate;
import com.kodilla.ClothesFactoryBackend.domain.ExchangeRateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ExchangeRatesMapperTestSuite {

    @InjectMocks
    private ExchangeRatesMapper exchangeRatesMapper;

    @Test
    public void testMapToExchangeRateDto() {
        //Given
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .fromCurrency("PLN")
                .toCurrency("ENG")
                .currencyRate(new BigDecimal(0.23))
                .build();

        //When
        ExchangeRateDto exchangeRateDto = exchangeRatesMapper.mapToExchangeRateDto(exchangeRate);

        //Then
        assertEquals("PLN", exchangeRateDto.getFromCurrency());
        assertEquals("ENG", exchangeRateDto.getToCurrency());
        assertEquals(new BigDecimal(0.23), exchangeRate.getCurrencyRate());
    }
}
