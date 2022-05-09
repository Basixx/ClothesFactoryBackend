package com.kodilla.ClothesFactoryBackend.domain;

import com.kodilla.ClothesFactoryBackend.auxiliary.Shipment;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "ORDERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private BigDecimal totalOrderPrice;

    @NotNull
    private boolean paid;

    @NotNull
    private boolean sent;

    @NotNull
    @Enumerated
    private Shipment shipment;

    @NotNull
    private BigDecimal shippingPrice;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            targetEntity = Cloth.class,
            mappedBy = "order"
    )
    private List<Cloth> clothesList;
}