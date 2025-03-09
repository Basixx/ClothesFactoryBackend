package com.clothes.factory.controller;

import com.clothes.factory.domain.PaymentHistoryDto;
import com.clothes.factory.facade.PaymentHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/paymentHistory")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryFacade paymentHistoryFacade;

    @GetMapping
    public ResponseEntity<List<PaymentHistoryDto>> getAllPaymentHistory() {
        return ResponseEntity.ok(paymentHistoryFacade.getAllPaymentHistory());
    }

}
