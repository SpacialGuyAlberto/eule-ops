package com.euleops.auth.model;

import com.euleops.auth.constants.Roles;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", schema="auth")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="keycloak_id", nullable=false)
    UUID keycloakId;

    @Column(nullable = false)
    private String name;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Roles role;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = false;

    @Column(nullable = true)
    private String activationToken;

    @Column(nullable = true, unique = true)
    private String affiliateLink;

    @Column(nullable = true, unique = true)
    private String promoCode;

    @Column(nullable = true)
    private Double commissionRate;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    @PrePersist
    protected void onCreate() {
        if (this.activationToken == null) {
            this.activationToken = generateActivationToken();
        }
    }

    private String generateActivationToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

