package ru.meetpoint.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.meetpoint.authservice.data.entity.UserCredentials;
import ru.meetpoint.authservice.data.entity.UserData;
import ru.meetpoint.authservice.data.enums.AuthProvider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {

    List<UserCredentials> findByUserData(UserData userData);

    List<UserCredentials> findAllByUserData_UserId(UUID userId);

    @Query("""
            SELECT c FROM UserCredentials c
            WHERE c.userData.state != ru.meetpoint.authservice.data.enums.State.DELETED
            AND c.providerKey = :providerKey
            AND c.providerType = :providerType
            """)
    Optional<UserCredentials> findActiveByProviderKeyAndProviderType(
            @Param("providerKey") String providerKey,
            @Param("providerType") AuthProvider providerType
    );
}
