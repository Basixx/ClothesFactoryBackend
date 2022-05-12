package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.PaymentHistoryDto;
import com.kodilla.ClothesFactoryBackend.mapper.PaymentHistoryMapper;
import com.kodilla.ClothesFactoryBackend.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentHistoryFacade {
    private final PaymentHistoryService paymentHistoryService;
    private final PaymentHistoryMapper paymentHistoryMapper;

    public List<PaymentHistoryDto> getAllPaymentHistory() {
        return paymentHistoryMapper.mapToPaymentHistoryDtoList(paymentHistoryService.getAllPaymentHistory());
    }
}