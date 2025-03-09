package com.kodilla.ClothesFactoryBackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity(name = "USERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    @Column(unique = true)
    private String emailAddress;

    @NotNull
    private String password;

    @NotNull
    private String street;

    @NotNull
    private String streetAndApartmentNumber;

    @NotNull
    private String city;

    @NotNull
    private String postCode;

    @OneToOne
    @JoinColumn(name = "ID_CART")
    private Cart cart;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            targetEntity = Order.class,
            mappedBy = "user"
    )
    private List<Order> ordersList;

    @Override
    public String toString() {
        return street + ", " + streetAndApartmentNumber + ", " + city + ", " + postCode;
    }

}
