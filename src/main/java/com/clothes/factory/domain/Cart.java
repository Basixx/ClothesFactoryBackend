package com.clothes.factory.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "CARTS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private BigDecimal totalPrice;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            targetEntity = Cloth.class,
            mappedBy = "cart"
    )
    private List<Cloth> clothesList;

}
