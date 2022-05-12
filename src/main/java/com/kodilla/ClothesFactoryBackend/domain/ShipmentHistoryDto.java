package com.kodilla.ClothesFactoryBackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentHistoryDto {
    private Long id;
    private LocalDateTime shipmentTime;
    private Long orderId;
    private String userMail;
}