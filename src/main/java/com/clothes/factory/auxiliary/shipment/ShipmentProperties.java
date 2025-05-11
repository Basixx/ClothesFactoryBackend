package com.clothes.factory.auxiliary.shipment;

import com.clothes.factory.auxiliary.ShipmentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "shipment")
@Getter
@Setter
public class ShipmentProperties {

    private Map<ShipmentMethod, MethodProperties> methods = new HashMap<>();

    @Getter
    @Setter
    public static class MethodProperties {

        private BigDecimal price;
        private int days;

    }

}
