package com.kodilla.ClothesFactoryBackend.mapper;

import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistory;
import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistoryDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentHistoryMapper {

    public ShipmentHistoryDto mapToShipmentHistoryDto(final ShipmentHistory shipmentHistory) {
        return ShipmentHistoryDto.builder()
                .shipmentTime(shipmentHistory.getShipmentTime())
                .orderId(shipmentHistory.getOrderId())
                .userMail(shipmentHistory.getUserMail())
                .build();
    }

    public List<ShipmentHistoryDto> mapToShipmentHistoryDtoList(final List<ShipmentHistory> shipmentHistories) {
        return shipmentHistories.stream()
                .map(this::mapToShipmentHistoryDto)
                .collect(Collectors.toList());
    }
}