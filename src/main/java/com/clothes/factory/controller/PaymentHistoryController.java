package com.clothes.factory.controller;

import com.clothes.factory.domain.PaymentHistoryDto;
import com.clothes.factory.facade.PaymentHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/paymentHistory")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryFacade paymentHistoryFacade;

    @GetMapping
    @ResponseStatus(OK)
    public List<PaymentHistoryDto> getPaymentHistory(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return paymentHistoryFacade.getAllPaymentHistory(page, size);
    }

}
