package com.kodilla.ClothesFactoryBackend.auxiliary;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Prices {
    public static BigDecimal findPrice(final Fashion fashion){
        switch (fashion){
            case T_SHIRT:
                return new BigDecimal(50);
            case LONGSLEEVE:
                return new BigDecimal(70);
            case HOODIE:
                return new BigDecimal(100);
            default:
                return BigDecimal.ZERO;
        }
    }
}