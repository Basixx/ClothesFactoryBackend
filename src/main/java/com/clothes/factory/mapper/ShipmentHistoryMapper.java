package com.clothes.factory.mapper;

import com.clothes.factory.domain.ShipmentHistory;
import com.clothes.factory.domain.ShipmentHistoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentHistoryMapper {

    public ShipmentHistoryDto mapToShipmentHistoryDto(final ShipmentHistory shipmentHistory) {
        return ShipmentHistoryDto.builder()
                .shipmentTime(shipmentHistory.getShipmentTime())
                .orderId(shipmentHistory.getOrderId())
                .userMail(shipmentHistory.getUserMail())
                .shippingCompany(shipmentHistory.getShippingCompany())
                .build();
    }

    public List<ShipmentHistoryDto> mapToShipmentHistoryDtoList(final List<ShipmentHistory> shipmentHistories) {
        return shipmentHistories.stream()
                .map(this::mapToShipmentHistoryDto)
                .toList();
    }

}
