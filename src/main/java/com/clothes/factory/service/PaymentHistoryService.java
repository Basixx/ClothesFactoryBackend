package com.clothes.factory.service;

import com.clothes.factory.domain.PaymentHistory;
import com.clothes.factory.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public List<PaymentHistory> getPaymentHistory(int page, int size) {
        return paymentHistoryRepository.findAll(PageRequest.of(page, size)).getContent();
    }

}
