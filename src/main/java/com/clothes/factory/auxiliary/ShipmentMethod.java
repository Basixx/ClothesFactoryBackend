package com.clothes.factory.auxiliary;

import java.math.BigDecimal;

public enum ShipmentMethod {

    FEDEX(new BigDecimal(10), 4),
    DHL(new BigDecimal(15), 3),
    UPS(new BigDecimal(20), 3),
    IN_POST(new BigDecimal(12), 5);

    private final BigDecimal shippingPrice;
    private final int deliveryDays;

    ShipmentMethod(BigDecimal shippingPrice, int deliveryDays) {
        this.shippingPrice = shippingPrice;
        this.deliveryDays = deliveryDays;
    }

    public BigDecimal shippingPrice() {
        return shippingPrice;
    }

    public int deliveryDays() {
        return deliveryDays;
    }

}
