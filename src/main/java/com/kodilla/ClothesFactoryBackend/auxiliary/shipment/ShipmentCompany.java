package com.kodilla.ClothesFactoryBackend.auxiliary.shipment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ShipmentCompany {
    private final String name;

    protected ShipmentPrice shipmentPrice;

    public BigDecimal setPrice() {
        return shipmentPrice.setShippingPrice();
    }
}
