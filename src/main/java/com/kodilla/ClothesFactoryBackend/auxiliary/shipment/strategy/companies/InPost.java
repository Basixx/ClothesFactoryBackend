package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.prices.InPostSetting;

public class InPost extends ShipmentCompany {

    public InPost() {
        this.shipmentSetting = new InPostSetting();
    }
}