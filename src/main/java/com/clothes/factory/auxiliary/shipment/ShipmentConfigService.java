package com.clothes.factory.auxiliary.shipment;

import com.clothes.factory.auxiliary.ShipmentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShipmentConfigService {

    private final ShipmentProperties shipmentProperties;

    public BigDecimal getShippingPrice(ShipmentMethod method) {
        return shipmentProperties.getMethods()
                .get(method)
                .getPrice();
    }

    public int getDeliveryDays(ShipmentMethod method) {
        return shipmentProperties.getMethods()
                .get(method)
                .getDays();
    }

}
