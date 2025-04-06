package com.clothes.factory.mapper;

import com.clothes.factory.domain.PaymentHistory;
import com.clothes.factory.domain.PaymentHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryMapper {

    public PaymentHistoryDto mapToPaymentHistoryDto(final PaymentHistory paymentHistory) {
        return PaymentHistoryDto.builder()
                .id(paymentHistory.getId())
                .paymentTime(paymentHistory.getPaymentTime())
                .orderId(paymentHistory.getOrderId())
                .userMail(paymentHistory.getUserMail())
                .price(paymentHistory.getPrice())
                .build();
    }

    public List<PaymentHistoryDto> mapToPaymentHistoryDtoList(final List<PaymentHistory> paymentHistories) {
        return paymentHistories.stream()
                .map(this::mapToPaymentHistoryDto)
                .toList();
    }

}
