package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentPrice;
import java.math.BigDecimal;

public class InPostPrice implements ShipmentPrice {

    @Override
    public BigDecimal setShippingPrice() {
        return new BigDecimal(12);
    }

    @Override
    public int setDeliveryDays() {
        return 5;
    }
}
