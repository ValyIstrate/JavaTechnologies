package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "one_time_password")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OneTimePassword implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "expiration_date")
    private Date expirationDate;
}