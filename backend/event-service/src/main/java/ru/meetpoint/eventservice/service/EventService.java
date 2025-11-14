package ru.meetpoint.eventservice.service;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;

import java.util.Set;
import java.util.UUID;

public interface EventService {

    Page<EventShortResponse> getAll(Pageable pageable);

    Set<EventShortResponse> getMainPage();

    Page<EventShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest);

    EventDetailedResponse getById(UUID eventId) throws BadRequestException;

    OperationResponse create(EventRequest eventRequest, UUID userId);

    OperationResponse update(UUID eventId, EventRequest eventRequest, UUID userId);

    OperationResponse delete(UUID eventId, UUID userId);
}
