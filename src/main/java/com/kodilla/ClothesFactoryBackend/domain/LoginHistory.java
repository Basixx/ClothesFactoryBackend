package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "LOGIN_HISTORY")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistory {
    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private LocalDateTime loginTime;

    @NotNull
    private String userMail;

    @NotNull
    private boolean succeed;
}