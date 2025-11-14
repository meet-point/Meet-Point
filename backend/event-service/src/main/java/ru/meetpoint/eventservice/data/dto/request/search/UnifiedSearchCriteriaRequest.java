package ru.meetpoint.eventservice.data.dto.request.search;

import lombok.Builder;
import org.springframework.data.domain.Sort;

import java.util.Set;
import java.util.UUID;

@Builder
public record UnifiedSearchCriteriaRequest(
        String searchRequest,
        Set<UUID> categoryIds,
        Sort.Direction sortDirection,
        int page,
        int size
) {
}
