package ru.meetpoint.authservice.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.authservice.data.enums.Role;
import ru.meetpoint.authservice.data.enums.State;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static ru.meetpoint.authservice.config.property.ValidationConstants.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_data")
public class UserData {

    @Id
    @UuidGenerator
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = EMAIL_MAX_LENGTH)
    private String email;

    @Column(name = "phone_number", length = PHONE_NUMBER_LENGTH)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "last_login")
    private Timestamp dateOfLastLogin;

    @Column(name = "created_at")
    private Timestamp dateOfCreation;

    @Column(name = "updated_at")
    private Timestamp dateOfLastUpdate;

    @Builder.Default
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCredentials> credentials = new HashSet<>();
}
