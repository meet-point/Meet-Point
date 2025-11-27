package ru.meetpoint.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.meetpoint.eventservice.api.OrganizationApi;
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.service.OrganizationManagerService;
import ru.meetpoint.eventservice.service.OrganizationService;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

    private final OrganizationService organizationService;

    private final OrganizationManagerService organizationManagerService;

    @Override
    public Page<OrganizationShortResponse> getAll(Pageable pageable) {
        return organizationService.getAll(pageable);
    }

    @Override
    public Set<OrganizationShortResponse> getForMainPage() {
        return organizationService.getForMainPage();
    }

    @Override
    public Page<OrganizationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        return organizationService.getByCriteria(searchCriteriaRequest);
    }

    @Override
    public OrganizationDetailedResponse getById(UUID organizationId) {
        return organizationService.getById(organizationId);
    }

    @Override
    public OperationResponse create(OrganizationRequest organizationRequest, UnifiedAuthPrincipal authPrincipal) {
        return organizationService.create(organizationRequest, authPrincipal);
    }

    @Override
    public OperationResponse update(UUID organizationId, OrganizationRequest organizationRequest, UnifiedAuthPrincipal authPrincipal) {
        return organizationService.update(organizationId, organizationRequest, authPrincipal.getUserId());
    }

    @Override
    public OperationResponse delete(UUID organizationId, UnifiedAuthPrincipal authPrincipal) {
        return organizationService.delete(organizationId, authPrincipal.getUserId());
    }

    @Override
    public ManagerResponse getManager(UUID organizationId, UUID managerId, UnifiedAuthPrincipal authPrincipal) {
        return organizationManagerService.getManagerInfo(organizationId, managerId);
    }

    @Override
    public OperationResponse addManagerToOrganization(UUID organizationId, ManagerRequest managerRequest,
                                                       UnifiedAuthPrincipal authPrincipal) {
        return organizationManagerService.addManagerToOrganization(organizationId, managerRequest);
    }

    @Override
    public OperationResponse deleteManager(UUID organizationId, UUID managerId, UnifiedAuthPrincipal authPrincipal) {
        return organizationManagerService.deleteManagerFromOrganization(organizationId, managerId);
    }
}
