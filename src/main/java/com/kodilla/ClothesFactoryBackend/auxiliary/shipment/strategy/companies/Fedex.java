package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.prices.FedexSetting;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;

public class Fedex extends ShipmentCompany {

    public Fedex() {
        this.shipmentSetting = new FedexSetting();
    }
}