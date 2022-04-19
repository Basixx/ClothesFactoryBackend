package com.kodilla.ClothesFactoryBackend.auxiliary;

import lombok.Getter;
import java.util.Map;

@Getter
public class ClothesOptions {

    private static final Map<Fashion, String> fashionMap = Map.of(
            Fashion.T_SHIRT, "T-Shirt",
            Fashion.LONGSLEEVE, "Longsleeve",
            Fashion.HOODIE, "Hoodie"
    );

    private static final Map<Color, String> colorMap = Map.of(
            Color.RED, "RED",
            Color.BLUE, "BLUE",
            Color.YELLOW, "YELLOW",
            Color.GREEN, "GREEN",
            Color.PINK, "PINK",
            Color.WHITE, "WHITE",
            Color.BLACK, "BLACK"
    );

    private static final Map<Font, String> fontMap = Map.of(
            Font.TIMES_NEW_ROMAN, "Times New Roman",
            Font.ROBOTO_MONO, "Roboto Mono",
            Font.ARIAL, "Arial",
            Font.UPDOCK, "UpDock",
            Font.LOBSTER, "Lobster",
            Font.COMIC_SANS, "Comic Sans"
    );

    private static final Map<Size, String> sizeMap = Map.of(
            Size.XS, "XS",
            Size.S, "S",
            Size.M, "M",
            Size.L, "L",
            Size.XL, "XL",
            Size.XXL, "XXL"
    );

    public static String getFashion(String fashion) {
        return fashionMap.get(fashion);
    }
}