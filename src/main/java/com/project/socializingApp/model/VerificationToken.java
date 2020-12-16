package com.project.socializingApp.model;

import jdk.jfr.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expireDate;
}
