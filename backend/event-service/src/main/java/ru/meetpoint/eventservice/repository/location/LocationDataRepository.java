package ru.meetpoint.eventservice.repository.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.location.LocationData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationDataRepository extends JpaRepository<LocationData, UUID>,
        JpaSpecificationExecutor<LocationData>, PagingAndSortingRepository<LocationData, UUID> {

    @Query(value = """
        SELECT * FROM location_data
        WHERE public_status = 'PUBLIC'
""", countQuery = """
        SELECT COUNT(*) FROM location_data
        WHERE public_status = 'PUBLIC'
""", nativeQuery = true)
    Page<LocationData> findAllPageable(Pageable pageable);

    @Query(value = """
            SELECT * FROM location_data
            WHERE public_status = 'PUBLIC'
            ORDER BY label
            LIMIT 20 OFFSET 0;
    """, nativeQuery = true)
    List<LocationData> findForMainPage();

    @EntityGraph(attributePaths = {
            "locationAdditional",
            "events",
            "organizationData",
            "organizationData.managers"
    }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<LocationData> findById(UUID id);
}
