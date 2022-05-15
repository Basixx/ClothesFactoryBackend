package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.PaymentHistory;
import com.kodilla.ClothesFactoryBackend.domain.PaymentHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentHistoryMapperTestSuite {

    @InjectMocks
    private PaymentHistoryMapper paymentHistoryMapper;

    @Test
    public void testMapToPaymentHistoryDto() {
        //Given
        PaymentHistory paymentHistory = createPaymentHistory1();

        //When
        PaymentHistoryDto paymentHistoryDto = paymentHistoryMapper.mapToPaymentHistoryDto(paymentHistory);

        //Then
        assertEquals(LocalDateTime.of(2022, 4, 27, 13, 15), paymentHistoryDto.getPaymentTime());
        assertEquals(3L, paymentHistoryDto.getOrderId());
        assertEquals("test@mail.com", paymentHistoryDto.getUserMail());
        assertEquals(new BigDecimal(500), paymentHistoryDto.getPrice());
    }

    @Test
    public void testMapToPaymentHistoryDtoList() {
        //Given
        List<PaymentHistory> paymentHistoryList = new ArrayList<>();
        PaymentHistory paymentHistory1 = createPaymentHistory1();
        PaymentHistory paymentHistory2 = createPaymentHistory2();
        paymentHistoryList.add(paymentHistory1);
        paymentHistoryList.add(paymentHistory2);

        //When
        List<PaymentHistoryDto> paymentHistoryDtoList = paymentHistoryMapper.mapToPaymentHistoryDtoList(paymentHistoryList);

        //Then
        assertEquals(2, paymentHistoryDtoList.size());

        assertEquals(LocalDateTime.of(2022, 4, 27, 13, 15), paymentHistoryDtoList.get(0).getPaymentTime());
        assertEquals(3L, paymentHistoryDtoList.get(0).getOrderId());
        assertEquals("test@mail.com", paymentHistoryDtoList.get(0).getUserMail());
        assertEquals(new BigDecimal(500), paymentHistoryDtoList.get(0).getPrice());

        assertEquals(LocalDateTime.of(2022, 3, 22, 22, 0), paymentHistoryDtoList.get(1).getPaymentTime());
        assertEquals(6L, paymentHistoryDtoList.get(1).getOrderId());
        assertEquals("test123@mail.com", paymentHistoryDtoList.get(1).getUserMail());
        assertEquals(new BigDecimal(800), paymentHistoryDtoList.get(1).getPrice());
    }

    private PaymentHistory createPaymentHistory1() {
        return PaymentHistory.builder()
                .paymentTime(LocalDateTime.of(2022, 4, 27, 13, 15))
                .orderId(3L)
                .userMail("test@mail.com")
                .price(new BigDecimal(500))
                .build();
    }

    private PaymentHistory createPaymentHistory2() {
        return PaymentHistory.builder()
                .paymentTime(LocalDateTime.of(2022, 3, 22, 22, 0))
                .orderId(6L)
                .userMail("test123@mail.com")
                .price(new BigDecimal(800))
                .build();
    }
}
