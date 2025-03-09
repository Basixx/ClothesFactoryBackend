package com.clothes.factory.auxiliary.shipment.strategy;

import java.math.BigDecimal;

public interface ShipmentSetting {
    String getName();
    BigDecimal getShippingPrice();
    int getDeliveryDays();
}