package com.clothes.factory.auxiliary.shipment.strategy.prices;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentSetting;
import java.math.BigDecimal;

public class UpsSetting implements ShipmentSetting {

    @Override
    public String getName() {
        return "UPS";
    }
    @Override
    public BigDecimal getShippingPrice() {
        return new BigDecimal(20);
    }

    @Override
    public int getDeliveryDays() {
        return 3;
    }
}