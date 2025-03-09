package com.clothes.factory.service;

import com.clothes.factory.domain.ShipmentHistory;
import com.clothes.factory.repository.ShipmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ShipmentHistoryService {

    private final ShipmentHistoryRepository shipmentHistoryRepository;

    public List<ShipmentHistory> getAllShipmentHistory() {
        return shipmentHistoryRepository.findAll();
    }

}
