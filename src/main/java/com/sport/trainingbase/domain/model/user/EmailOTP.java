package com.sport.trainingbase.domain.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "emailotp",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class EmailOTP {
    @Id
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    Integer otp;

    @Column(nullable = false)
    boolean isVerified;

    @Column(nullable = false)
    LocalDateTime creationTime;
}
