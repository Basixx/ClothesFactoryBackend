package com.clothes.factory.facade;

import com.clothes.factory.domain.ShipmentHistoryDto;
import com.clothes.factory.mapper.ShipmentHistoryMapper;
import com.clothes.factory.service.ShipmentHistoryService;
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
