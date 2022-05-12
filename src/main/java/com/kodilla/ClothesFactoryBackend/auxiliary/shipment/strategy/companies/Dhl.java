package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.prices.DhlSetting;

public class Dhl extends ShipmentCompany {
    public Dhl() {
        this.shipmentSetting = new DhlSetting();
    }
}
