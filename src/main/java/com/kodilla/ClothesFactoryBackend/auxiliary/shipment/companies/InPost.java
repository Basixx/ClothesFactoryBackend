package com.kodilla.ClothesFactoryBackend.auxiliary.shipment.companies;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.prices.InPostPrice;

public class InPost extends ShipmentCompany {

    public InPost(String name) {
        super(name);
        this.shipmentPrice = new InPostPrice();
    }
}
