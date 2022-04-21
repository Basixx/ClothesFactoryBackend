package com.kodilla.ClothesFactoryBackend.auxiliary;

import java.math.BigDecimal;

public class Prices {
    public final BigDecimal findPrice(final Fashion fashion){
        switch (fashion){
            case T_SHIRT:
                return new BigDecimal(50);
            case LONGSLEEVE:
                return new BigDecimal(70);
            case HOODIE:
                return new BigDecimal(100);
            default:
                return new BigDecimal(0);
        }
    }
}