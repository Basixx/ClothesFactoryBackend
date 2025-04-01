package com.clothes.factory.auxiliary.shipment.strategy.companies;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.auxiliary.shipment.strategy.prices.FedexSetting;

public class Fedex extends ShipmentCompany {

    public Fedex() {
        this.shipmentSetting = new FedexSetting();
    }

}
