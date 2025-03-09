package com.clothes.factory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "PAYMENT_HISTORY")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private Long id;

    @NotNull
    private LocalDateTime paymentTime;

    @NotNull
    private Long orderId;

    @NotNull
    private String userMail;

    @NotNull
    private BigDecimal price;

}
