package com.kodilla.ClothesFactoryBackend.domain;

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
    private String type;

    @NotNull
    private String color;

    @NotNull
    private String print;

    @NotNull
    private String font;

    @NotNull
    private String printColor;

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