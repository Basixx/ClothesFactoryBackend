package com.clothes.factory.auxiliary.shipment.strategy.companies;

import com.clothes.factory.auxiliary.shipment.strategy.prices.FedexSetting;
import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;

public class Fedex extends ShipmentCompany {

    public Fedex() {
        this.shipmentSetting = new FedexSetting();
    }
}