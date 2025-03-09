package com.kodilla.ClothesFactoryBackend.service;

import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistory;
import com.kodilla.ClothesFactoryBackend.repository.ShipmentHistoryRepository;
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
