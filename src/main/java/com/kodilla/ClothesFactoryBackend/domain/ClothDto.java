package com.kodilla.ClothesFactoryBackend.domain;

import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothDto {
    private Long id;
    private Fashion fashion;
    private Color color;
    private String print;
    private Font font;
    private Color printColor;
    private Size size;
    private int quantity;
    private BigDecimal price;
}