package com.kodilla.ClothesFactoryBackend.auxiliary.shipment;

import java.math.BigDecimal;

public interface ShipmentPrice {
    BigDecimal setShippingPrice();

    int setDeliveryDays();
}
