package com.kodilla.ClothesFactoryBackend.auxiliary;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class Prices {
    public static BigDecimal findClothPrice(final Fashion fashion) {
        switch (fashion) {
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

    public static BigDecimal findShippingPrice(final Shipment shipment) {
        switch (shipment) {
            case FEDEX:
                return new BigDecimal(10);
            case DHL:
                return new BigDecimal(15);
            case UPS:
                return new BigDecimal(20);
            case IN_POST:
                return new BigDecimal(12);
            default:
                return BigDecimal.ZERO;
        }
    }
}