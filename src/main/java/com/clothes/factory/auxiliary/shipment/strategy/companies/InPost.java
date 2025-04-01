package com.clothes.factory.auxiliary.shipment.strategy.companies;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import com.clothes.factory.auxiliary.shipment.strategy.prices.InPostSetting;

public class InPost extends ShipmentCompany {

    public InPost() {
        this.shipmentSetting = new InPostSetting();
    }

}
