package com.kodilla.ClothesFactoryBackend.controller;

import com.kodilla.ClothesFactoryBackend.domain.ShipmentHistoryDto;
import com.kodilla.ClothesFactoryBackend.facade.ShipmentHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/shipmentHistory")
@RequiredArgsConstructor
public class ShipmentHistoryController {
    private final ShipmentHistoryFacade shipmentHistoryFacade;

    @GetMapping
    public ResponseEntity<List<ShipmentHistoryDto>> getAllShipmentHistory() {
        return ResponseEntity.ok(shipmentHistoryFacade.getAllShipmentHistory());
    }
}