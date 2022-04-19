package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "USERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne
    @JoinColumn(name = "ID_CART")
    private Cart cart;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user"
    )
    private List<Order> ordersList;
}