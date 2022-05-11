package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentPrice;
import java.math.BigDecimal;

public class DhlPrice implements ShipmentPrice {

    @Override
    public BigDecimal setShippingPrice() {
        return new BigDecimal(15);
    }

    @Override
    public int setDeliveryDays() {
        return 3;
    }
}
