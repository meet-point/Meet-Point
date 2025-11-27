package ru.meetpoint.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.meetpoint.eventservice.api.EventApi;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.service.EventService;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventController implements EventApi {

    private final EventService eventService;

    @Override
    public Page<EventShortResponse> getAll(Pageable pageable) {
        return eventService.getAll(pageable);
    }

    @Override
    public Set<EventShortResponse> getForMainPage() {
        return eventService.getForMainPage();
    }

    @Override
    public Page<EventShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        return eventService.getByCriteria(searchCriteriaRequest);
    }

    @Override
    public EventDetailedResponse getById(UUID eventId) {
        return eventService.getById(eventId);
    }

    @Override
    public OperationResponse create(EventRequest eventRequest, UnifiedAuthPrincipal authPrincipal) {
        return eventService.create(eventRequest, authPrincipal.getUserId());
    }

    @Override
    public OperationResponse update(UUID eventId, EventRequest eventRequest, UnifiedAuthPrincipal authPrincipal) {
        return eventService.update(eventId, eventRequest, authPrincipal.getUserId());
    }

    @Override
    public OperationResponse delete(UUID eventId, UnifiedAuthPrincipal authPrincipal) {
        return eventService.delete(eventId, authPrincipal.getUserId());
    }
}
