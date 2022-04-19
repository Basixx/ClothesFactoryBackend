package com.kodilla.ClothesFactoryBackend.auxiliary;

import java.math.BigDecimal;

public class Prices {
    public final BigDecimal findPrice(final String type){
        switch (type){
            case "T-Shirt":
                return new BigDecimal(50);
            case "Longsleeve":
                return new BigDecimal(70);
            case "Hoodie":
                return new BigDecimal(100);
            default:
                return new BigDecimal(0);
        }
    }
}