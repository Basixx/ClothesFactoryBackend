package com.kodilla.ClothesFactoryBackend.domain;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @Transient
    private ShipmentCompany shipmentCompany;

    @NotNull
    private String shipmentCompanyName;

    @NotNull
    private BigDecimal shippingPrice;

    @NotNull
    private int deliveryDays;

    @NotNull
    private String address;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            targetEntity = Cloth.class,
            mappedBy = "order"
    )
    private List<Cloth> clothesList;
}