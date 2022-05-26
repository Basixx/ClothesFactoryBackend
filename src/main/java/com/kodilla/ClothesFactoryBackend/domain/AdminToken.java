package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "ADMIN_TOKEN")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminToken {
    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private String token;
}