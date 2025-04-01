package com.clothes.factory.auxiliary.shipment.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ShipmentCompany {

    protected ShipmentSetting shipmentSetting;

    public String getName() {
        return shipmentSetting.getName();
    }

    public BigDecimal getPrice() {
        return shipmentSetting.getShippingPrice();
    }

    public int getDeliveryDays() {
        return shipmentSetting.getDeliveryDays();
    }

}
