package ru.meetpoint.eventservice.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.event.EventAdditional;

import java.util.UUID;

public interface EventAdditionalRepository extends JpaRepository<EventAdditional, UUID>,
        PagingAndSortingRepository<EventAdditional, UUID> {
}
