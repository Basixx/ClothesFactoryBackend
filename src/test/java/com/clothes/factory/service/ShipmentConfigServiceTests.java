package com.clothes.factory.service;

import com.clothes.factory.auxiliary.ShipmentMethod;
import com.clothes.factory.auxiliary.shipment.ShipmentConfigService;
import com.clothes.factory.auxiliary.shipment.ShipmentProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.clothes.factory.auxiliary.ShipmentMethod.FEDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShipmentConfigServiceTests {

    @Mock
    private ShipmentProperties shipmentProperties;

    @InjectMocks
    private ShipmentConfigService shipmentConfigService;

    @Test
    void shouldReturnShippingPriceAndDeliveryDaysForFedex() {
        // Given
        ShipmentProperties.MethodProperties fedexProps = new ShipmentProperties.MethodProperties();
        fedexProps.setPrice(new BigDecimal("10.00"));
        fedexProps.setDays(4);

        Map<ShipmentMethod, ShipmentProperties.MethodProperties> methods = new HashMap<>();
        methods.put(FEDEX, fedexProps);

        when(shipmentProperties.getMethods()).thenReturn(methods);

        // When
        BigDecimal price = shipmentConfigService.getShippingPrice(FEDEX);
        int days = shipmentConfigService.getDeliveryDays(FEDEX);

        // Then
        assertEquals(new BigDecimal("10.00"), price);
        assertEquals(4, days);
    }

}
