package ru.meetpoint.eventservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

public interface OrganizationService {
    Page<OrganizationShortResponse> getAll(Pageable pageable);

    Set<OrganizationShortResponse> getForMainPage();

    Page<OrganizationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest);

    OrganizationDetailedResponse getById(UUID organizationId);

    OperationResponse create(OrganizationRequest organizationRequest, UnifiedAuthPrincipal authPrincipal);

    OperationResponse update(UUID organizationId, OrganizationRequest organizationRequest, UUID userId);

    OperationResponse delete(UUID organizationId, UUID userId);
}
