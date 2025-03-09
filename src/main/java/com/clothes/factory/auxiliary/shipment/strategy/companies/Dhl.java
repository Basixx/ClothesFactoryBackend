package com.clothes.factory.auxiliary.shipment.strategy.companies;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.auxiliary.shipment.strategy.prices.DhlSetting;

public class Dhl extends ShipmentCompany {
    public Dhl() {
        this.shipmentSetting = new DhlSetting();
    }
}
