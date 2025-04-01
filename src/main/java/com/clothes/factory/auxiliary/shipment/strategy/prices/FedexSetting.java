package com.clothes.factory.auxiliary.shipment.strategy.prices;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentSetting;

import java.math.BigDecimal;

public class FedexSetting implements ShipmentSetting {

    @Override
    public String getName() {
        return "FEDEX";
    }

    @Override
    public BigDecimal getShippingPrice() {
        return new BigDecimal(10);
    }

    @Override
    public int getDeliveryDays() {
        return 4;
    }

}
