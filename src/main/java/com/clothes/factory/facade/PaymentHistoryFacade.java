package com.clothes.factory.facade;

import com.clothes.factory.domain.PaymentHistoryDto;
import com.clothes.factory.mapper.PaymentHistoryMapper;
import com.clothes.factory.service.PaymentHistoryService;
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

    public List<PaymentHistoryDto> getAllPaymentHistory(int page, int size) {
        return paymentHistoryMapper.mapToPaymentHistoryDtoList(paymentHistoryService.getPaymentHistory(page, size));
    }

}
