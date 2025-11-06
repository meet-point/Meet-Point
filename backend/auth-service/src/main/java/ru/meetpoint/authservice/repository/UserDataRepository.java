package ru.meetpoint.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.authservice.data.entity.UserData;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, UUID>, PagingAndSortingRepository<UserData, UUID> {

    boolean existsByEmail(String email);

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findUserDataByUserId(UUID userId);
}
