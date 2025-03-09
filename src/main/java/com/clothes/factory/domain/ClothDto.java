package com.clothes.factory.domain;

import com.clothes.factory.auxiliary.Color;
import com.clothes.factory.auxiliary.Fashion;
import com.clothes.factory.auxiliary.Font;
import com.clothes.factory.auxiliary.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
