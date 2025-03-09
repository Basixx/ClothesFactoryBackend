package com.clothes.factory.auxiliary.shipment.strategy.companies;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.auxiliary.shipment.strategy.prices.UpsSetting;

public class Ups extends ShipmentCompany {

    public Ups() {
        this.shipmentSetting = new UpsSetting();
    }
}