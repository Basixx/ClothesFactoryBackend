package com.kodilla.ClothesFactoryBackend.strategy;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.CompanySetter;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShipmentStrategyTestSuite {

    @Autowired
    CompanySetter companySetter;

    @Test
    void testCompanySetterFedex() {
        //Given & When
        ShipmentCompany fedex = companySetter.setCompany(ShipmentMethod.FEDEX);

        //Then
        assertEquals("FEDEX", fedex.getName());
        assertEquals(new BigDecimal(10), fedex.getPrice());
        assertEquals(4, fedex.getDeliveryDays());
    }

    @Test
    void testCompanySetterDhl() {
        //Given & When
        ShipmentCompany fedex = companySetter.setCompany(ShipmentMethod.DHL);

        //Then
        assertEquals("DHL", fedex.getName());
        assertEquals(new BigDecimal(15), fedex.getPrice());
        assertEquals(3, fedex.getDeliveryDays());
    }

    @Test
    void testCompanySetterInPost() {
        //Given & When
        ShipmentCompany fedex = companySetter.setCompany(ShipmentMethod.IN_POST);

        //Then
        assertEquals("InPost", fedex.getName());
        assertEquals(new BigDecimal(12), fedex.getPrice());
        assertEquals(5, fedex.getDeliveryDays());
    }

    @Test
    void testCompanySetterUps() {
        //Given & When
        ShipmentCompany fedex = companySetter.setCompany(ShipmentMethod.UPS);

        //Then
        assertEquals("UPS", fedex.getName());
        assertEquals(new BigDecimal(20), fedex.getPrice());
        assertEquals(3, fedex.getDeliveryDays());
    }
}
