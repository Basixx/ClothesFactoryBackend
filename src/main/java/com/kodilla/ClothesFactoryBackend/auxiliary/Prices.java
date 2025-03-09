package com.kodilla.ClothesFactoryBackend.auxiliary;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Prices {

    public BigDecimal findClothPrice(final Fashion fashion) {
        return switch (fashion) {
            case T_SHIRT -> new BigDecimal(50);
            case LONGSLEEVE -> new BigDecimal(70);
            case HOODIE -> new BigDecimal(100);
        };
    }

}
