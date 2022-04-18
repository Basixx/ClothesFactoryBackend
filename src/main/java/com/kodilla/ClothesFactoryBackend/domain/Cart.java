package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
            targetEntity = Cloth.class,
            mappedBy = "cart"
    )
    private List<Cloth> clothesList;

}