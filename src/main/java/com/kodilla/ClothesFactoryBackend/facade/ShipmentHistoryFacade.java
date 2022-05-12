package com.kodilla.ClothesFactoryBackend.facade;

import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistoryDto;
import com.kodilla.ClothesFactoryBackend.mapper.ShipmentHistoryMapper;
import com.kodilla.ClothesFactoryBackend.service.ShipmentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentHistoryFacade {
    private final ShipmentHistoryService shipmentHistoryService;
    private final ShipmentHistoryMapper shipmentHistoryMapper;

    public List<ShipmentHistoryDto> getAllShipmentHistory() {
        return shipmentHistoryMapper.mapToShipmentHistoryDtoList(shipmentHistoryService.getAllShipmentHistory());
    }
}