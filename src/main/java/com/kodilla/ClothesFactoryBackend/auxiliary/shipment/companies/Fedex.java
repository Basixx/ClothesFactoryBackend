package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices.FedexPrice;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentCompany;

public class Fedex extends ShipmentCompany {

    public Fedex(String name) {
        super(name);
        this.shipmentPrice = new FedexPrice();
    }
}
