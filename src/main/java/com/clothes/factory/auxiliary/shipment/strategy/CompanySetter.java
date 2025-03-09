package com.clothes.factory.auxiliary.shipment.strategy;

import com.clothes.factory.auxiliary.shipment.strategy.companies.Dhl;
import com.clothes.factory.auxiliary.shipment.strategy.companies.Fedex;
import com.clothes.factory.auxiliary.shipment.strategy.companies.InPost;
import com.clothes.factory.auxiliary.shipment.strategy.companies.Ups;
import org.springframework.stereotype.Component;

@Component
public class CompanySetter {
    public ShipmentCompany setCompany(final ShipmentMethod shipmentMethod) {
        return switch (shipmentMethod) {
            case FEDEX -> new Fedex();
            case DHL -> new Dhl();
            case UPS -> new Ups();
            case IN_POST -> new InPost();
        };
    }
}