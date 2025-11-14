package ru.meetpoint.eventservice.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.location.LocationData;

import java.util.UUID;

public interface LocationDataRepository extends JpaRepository<LocationData, UUID>,
        PagingAndSortingRepository<LocationData, UUID> {
}
