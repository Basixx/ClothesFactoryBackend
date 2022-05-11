package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices.DhlPrice;

public class Dhl extends ShipmentCompany {

    public Dhl(String name) {
        super(name);
        this.shipmentPrice = new DhlPrice();
    }
}
