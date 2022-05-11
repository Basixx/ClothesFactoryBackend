package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices.UpsPrice;

public class Ups extends ShipmentCompany {

    public Ups(String name) {
        super(name);
        this.shipmentPrice = new UpsPrice();
    }
}
