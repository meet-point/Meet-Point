package ru.meetpoint.eventservice.service.impl;

import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.event.EventDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.event.EventShortResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;
import ru.meetpoint.eventservice.data.entity.category.CategoryData;
import ru.meetpoint.eventservice.data.entity.event.EventAdditional;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.exception.NotFoundException;
import ru.meetpoint.eventservice.mapper.event.EventDataMapper;
import ru.meetpoint.eventservice.repository.category.CategoryDataRepository;
import ru.meetpoint.eventservice.repository.event.EventDataRepository;
import ru.meetpoint.eventservice.repository.location.LocationDataRepository;
import ru.meetpoint.eventservice.service.EventService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final CategoryDataRepository categoryDataRepository;

    private final LocationDataRepository locationDataRepository;

    private final EventDataRepository eventDataRepository;

    private final EventDataMapper eventDataMapper;

    @Override
    public Page<EventShortResponse> getAll(Pageable pageable) {
        return eventDataRepository
                .findAllPageable(pageable)
                .map(eventDataMapper::toShortResponse);
    }

    @Override
    public Set<EventShortResponse> getForMainPage() {
        return eventDataRepository
                .findForMainPage().stream()
                .map(eventDataMapper::toShortResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<EventShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        Pageable pageable = PageRequest.of(
                searchCriteriaRequest.page(),
                searchCriteriaRequest.size(),
                Sort.by(searchCriteriaRequest.sortDirection())
        );

        return eventDataRepository
                .findAll(Specification
                        .where(containsSearchRequest(searchCriteriaRequest.searchRequest()))
                        .and(containsCategories(searchCriteriaRequest.categoryIds())), pageable)
                .map(eventDataMapper::toShortResponse);
    }

    @Override
    @Transactional
    public EventDetailedResponse getById(UUID eventId) {
        EventData eventData = eventDataRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event data with id=%s was not found!".formatted(eventId)));

        if (eventData.getEventAdditional() == null) {
            throw new NotFoundException("Event additional data with id=%s was not found!".formatted(eventId));
        }

        return eventDataMapper.toDetailedResponse(eventData);
    }

    @Override
    @Transactional
    public OperationResponse create(EventRequest eventRequest, UUID userId) {
        EventData eventData = eventDataMapper.toEntity(eventRequest);

        CategoryData categoryData = categoryDataRepository.findById(eventRequest.categoryId())
                .orElseThrow(() -> new NotFoundException("Category data with id=%s was not found!"
                        .formatted(eventRequest.categoryId()))
                );

        LocationData locationData = locationDataRepository.findById(eventRequest.locationId())
                .orElseThrow(() -> new NotFoundException("Location data with id=%s was not found!"
                        .formatted(eventRequest.locationId()))
                );

        EventAdditional eventAdditional = EventAdditional.builder()
                .publicationDate(eventRequest.publishTime())
                .creatorId(userId)
                .createdAt(Timestamp.from(Instant.now()))
                .eventData(eventData)
                .build();

        eventData.setEventAdditional(eventAdditional);
        eventData.setCategoryData(categoryData);
        eventData.setLocationData(locationData);

        eventData = eventDataRepository.save(eventData);

        return OperationResponse.builder()
                .entityId(eventData.getEventId())
                .isSuccess(true)
                .build();
    }

    @Override
    @Transactional
    public OperationResponse update(UUID eventId, EventRequest eventRequest, UUID userId) {
        EventData eventData = eventDataRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event data with id=%s was not found!".formatted(eventId)));

        eventDataMapper.toUpdatedEntity(eventData, eventRequest);

        if (!eventData.getCategoryData().getCategoryId().equals(eventRequest.categoryId())) {
            CategoryData categoryData = categoryDataRepository.findById(eventRequest.categoryId())
                    .orElseThrow(() -> new NotFoundException("Category data with id=%s was not found!"
                            .formatted(eventRequest.categoryId()))
                    );
            eventData.setCategoryData(categoryData);
        }

        if (!eventData.getLocationData().getLocationId().equals(eventRequest.locationId())) {
            LocationData locationData = locationDataRepository.findById(eventRequest.locationId())
                    .orElseThrow(() -> new NotFoundException("Location data with id=%s was not found!"
                            .formatted(eventRequest.locationId()))
                    );
            eventData.setLocationData(locationData);
        }

        EventAdditional eventAdditional = eventData.getEventAdditional();
        eventAdditional.setLastUpdateBy(userId);
        eventAdditional.setLastUpdateAt(Timestamp.from(Instant.now()));

        eventDataRepository.save(eventData);

        return OperationResponse.builder()
                .isSuccess(true)
                .entityId(eventId)
                .build();
    }

    @Override
    public OperationResponse delete(UUID eventId, UUID userId) {
        eventDataRepository.deleteById(eventId);
        return OperationResponse.builder()
                .isSuccess(true)
                .build();
    }

    private Specification<EventData> containsSearchRequest(String searchRequest) {
        return (root, query, criteriaBuilder) -> (searchRequest == null || searchRequest.isEmpty()) ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("searchRequest")),
                        "%" + searchRequest.toLowerCase() + "%");
    }

    private Specification<EventData> containsCategories(Set<UUID> categoryIds) {
        return (root, query, criteriaBuilder) -> (categoryIds == null || categoryIds.isEmpty()) ?
                criteriaBuilder.conjunction() :
                root.join("category_data", JoinType.INNER).get("category_id").in(categoryIds);

    }
}
