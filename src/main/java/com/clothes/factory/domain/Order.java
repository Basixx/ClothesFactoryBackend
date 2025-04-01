package com.clothes.factory.domain;

import com.clothes.factory.auxiliary.shipment.strategy.ShipmentCompany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
