package com.clothes.factory.repository;

import com.clothes.factory.domain.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRatesRepository extends CrudRepository<ExchangeRate, Long> {

    ExchangeRate findByFromCurrencyAndToCurrency(String from, String to);

    Boolean existsByFromCurrencyAndToCurrency(String from, String to);

}
