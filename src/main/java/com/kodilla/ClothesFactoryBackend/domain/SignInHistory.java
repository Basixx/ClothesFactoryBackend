package com.kodilla.ClothesFactoryBackend.domain;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "SIGN_IN_HISTORY")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInHistory {
    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private LocalDateTime signInTime;

    @NotNull
    private String userMail;

    @NotNull
    private String userNumber;
}