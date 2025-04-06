package com.clothes.factory.domain;

import com.clothes.factory.auxiliary.Color;
import com.clothes.factory.auxiliary.Fashion;
import com.clothes.factory.auxiliary.Font;
import com.clothes.factory.auxiliary.Size;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "CLOTHES")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;

    @Override
    public String toString() {
        return "Cloth: fashion: %s, color: %s, print: '%s', font: %s, printColor: %s, size: %s, quantity: %s, price: %s."
                .formatted(
                        fashion,
                        color,
                        print,
                        font,
                        printColor,
                        size,
                        quantity,
                        price
                );
    }

}
