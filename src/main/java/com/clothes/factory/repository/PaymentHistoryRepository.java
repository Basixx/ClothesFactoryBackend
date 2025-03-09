package com.clothes.factory.repository;

import com.clothes.factory.domain.PaymentHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {

    @NonNull
    @Override
    List<PaymentHistory> findAll();

}
