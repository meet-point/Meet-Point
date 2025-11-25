package ru.meetpoint.eventservice.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.location.LocationAdditional;

import java.util.UUID;

public interface LocationAdditionalRepository extends JpaRepository<LocationAdditional, UUID>,
        PagingAndSortingRepository<LocationAdditional, UUID> {
}
