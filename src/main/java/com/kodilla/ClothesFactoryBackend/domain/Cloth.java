package com.kodilla.ClothesFactoryBackend.domain;

import com.kodilla.ClothesFactoryBackend.auxiliary.Color;
import com.kodilla.ClothesFactoryBackend.auxiliary.Fashion;
import com.kodilla.ClothesFactoryBackend.auxiliary.Font;
import com.kodilla.ClothesFactoryBackend.auxiliary.Size;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name="CLOTHES")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cloth {
    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    @Enumerated
    private Fashion fashion;

    @NotNull
    @Enumerated
    private Color color;

    @NotNull
    private String print;

    @NotNull
    @Enumerated
    private Font font;

    @NotNull
    @Enumerated
    private Color printColor;

    @NotNull
    @Enumerated
    private Size size;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "ID_CART")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ID_ORDER")
    private Order order;
}