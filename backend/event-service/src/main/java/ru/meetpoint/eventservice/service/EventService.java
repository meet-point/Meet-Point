package ru.meetpoint.eventservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

public interface EventService {

    Page<EventShortResponse> getAll(Pageable pageable);

    Set<EventShortResponse> getForMainPage();

    Page<EventShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest);

    EventDetailedResponse getById(UUID eventId);

    OperationResponse create(EventRequest eventRequest, UUID userId);

    OperationResponse update(UUID eventId, EventRequest eventRequest, UUID userId);

    OperationResponse delete(UUID eventId, UUID userId);
}
