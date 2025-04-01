package com.clothes.factory.auxiliary.shipment.strategy.prices;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentSetting;

import java.math.BigDecimal;

public class DhlSetting implements ShipmentSetting {

    @Override
    public String getName() {
        return "DHL";
    }

    @Override
    public BigDecimal getShippingPrice() {
        return new BigDecimal(15);
    }

    @Override
    public int getDeliveryDays() {
        return 3;
    }

}
