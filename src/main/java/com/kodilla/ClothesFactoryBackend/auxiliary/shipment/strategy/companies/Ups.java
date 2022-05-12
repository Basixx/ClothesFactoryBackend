package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.prices.UpsSetting;

public class Ups extends ShipmentCompany {

    public Ups() {
        this.shipmentSetting = new UpsSetting();
    }
}