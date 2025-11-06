package ru.meetpoint.authservice.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.authservice.data.enums.AuthProvider;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @UuidGenerator
    @Column(name = "credential_id")
    private UUID credentialId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData userData;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private AuthProvider providerType;

    @Column(name = "provider_key", nullable = false)
    private String providerKey;

    @Column(name = "hash_password")
    private String hashPassword;
}
