package com.clothes.factory.controller;

import com.clothes.factory.domain.ShipmentHistoryDto;
import com.clothes.factory.facade.ShipmentHistoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/shipmentHistory")
@RequiredArgsConstructor
public class ShipmentHistoryController {

    private final ShipmentHistoryFacade shipmentHistoryFacade;

    @GetMapping
    @ResponseStatus(OK)
    public List<ShipmentHistoryDto> getAllShipmentHistory(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return shipmentHistoryFacade.getAllShipmentHistory(page, size);
    }

}
