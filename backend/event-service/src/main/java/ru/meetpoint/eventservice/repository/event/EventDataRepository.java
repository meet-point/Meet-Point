package ru.meetpoint.eventservice.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.event.EventData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventDataRepository extends JpaRepository<EventData, UUID>,
        JpaSpecificationExecutor<EventData>, PagingAndSortingRepository<EventData, UUID> {

    @Query(value = """
        SELECT * FROM event_data
        WHERE publish_status = 'PUBLISHED'
""", countQuery = """
        SELECT COUNT(*) FROM event_data
        WHERE publish_status = 'PUBLISHED'
""", nativeQuery = true)
    Page<EventData> findAllPageable(Pageable pageable);

    @Query(value = """
            SELECT * FROM event_data
            WHERE publish_status = 'PUBLISHED'
            AND registered_now < max_allowed_people
            ORDER BY date_time
            LIMIT 20 OFFSET 0;
    """, nativeQuery = true)
    List<EventData> findForMainPage();

    @EntityGraph(attributePaths = {
            "eventAdditional",
            "categoryData",
            "locationData",
            "locationData.organizationData"
    }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<EventData> findById(UUID uuid);
}
