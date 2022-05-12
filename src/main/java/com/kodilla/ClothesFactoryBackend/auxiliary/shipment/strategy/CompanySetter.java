package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Dhl;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Fedex;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.InPost;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Ups;
import org.springframework.stereotype.Component;

@Component
public class CompanySetter {
    public ShipmentCompany setCompany(final ShipmentMethod shipmentMethod) {
        switch (shipmentMethod) {
            case FEDEX:
                return new Fedex();
            case DHL:
                return new Dhl();
            case UPS:
                return new Ups();
            case IN_POST:
                return new InPost();
            default:
                return new ShipmentCompany();
        }
    }
}