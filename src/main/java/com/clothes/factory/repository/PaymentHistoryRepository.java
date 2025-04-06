package com.clothes.factory.repository;

import com.clothes.factory.domain.PaymentHistory;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @NonNull
    @Override
    Page<PaymentHistory> findAll(@NonNull Pageable pageable);

}
