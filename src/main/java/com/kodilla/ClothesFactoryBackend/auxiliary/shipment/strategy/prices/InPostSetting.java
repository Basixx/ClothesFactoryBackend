package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.prices;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentSetting;
import java.math.BigDecimal;

public class InPostSetting implements ShipmentSetting {

    @Override
    public String getName() {
        return "InPost";
    }

    @Override
    public BigDecimal getShippingPrice() {
        return new BigDecimal(12);
    }

    @Override
    public int getDeliveryDays() {
        return 5;
    }
}
