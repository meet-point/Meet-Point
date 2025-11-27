package ru.meetpoint.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.dto.request.search.UnifiedSearchCriteriaRequest;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationDetailedResponse;
import ru.meetpoint.eventservice.data.dto.response.organization.OrganizationShortResponse;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationAdditional;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.error.exception.NotFoundException;
import ru.meetpoint.eventservice.mapper.OrganizationAdditionalMapper;
import ru.meetpoint.eventservice.mapper.OrganizationDataMapper;
import ru.meetpoint.eventservice.repository.organization.OrganizationDataRepository;
import ru.meetpoint.eventservice.service.OrganizationManagerService;
import ru.meetpoint.eventservice.service.OrganizationService;
import ru.meetpoint.security.starter.data.principal.UnifiedAuthPrincipal;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDataRepository organizationDataRepository;

    private final OrganizationDataMapper organizationDataMapper;

    private final OrganizationAdditionalMapper organizationAdditionalMapper;

    private final OrganizationManagerService organizationManagerService;

    @Override
    public Page<OrganizationShortResponse> getAll(Pageable pageable) {
        return organizationDataRepository
                .findAllPageable(pageable)
                .map(organizationDataMapper::toShortResponse);
    }

    @Override
    public Set<OrganizationShortResponse> getForMainPage() {
        return organizationDataRepository
                .findForMainPage().stream()
                .map(organizationDataMapper::toShortResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<OrganizationShortResponse> getByCriteria(UnifiedSearchCriteriaRequest searchCriteriaRequest) {
        Pageable pageable = PageRequest.of(
                searchCriteriaRequest.page(),
                searchCriteriaRequest.size(),
                Sort.by(searchCriteriaRequest.sortDirection())
        );

        return organizationDataRepository
                .findAll(Specification.where(containsSearchRequest(searchCriteriaRequest.searchRequest())), pageable)
                .map(organizationDataMapper::toShortResponse);
    }

    @Override
    public OrganizationDetailedResponse getById(UUID organizationId) {
        OrganizationData organizationData = organizationDataRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization data with id=%s was not found!".formatted(organizationId)));

        if (organizationData.getOrganizationAdditional() == null) {
            throw new NotFoundException("Organization additional data with id=%s was not found!".formatted(organizationId));
        }

        return organizationDataMapper.toDetailedResponse(organizationData);
    }

    @Override
    @Transactional
    public OperationResponse create(OrganizationRequest organizationRequest, UnifiedAuthPrincipal authPrincipal) {
        OrganizationData organizationData = organizationDataMapper.toEntity(organizationRequest);

        OrganizationAdditional organizationAdditional = organizationAdditionalMapper.toEntity(organizationRequest);
        organizationAdditional.setCreatorId(authPrincipal.getUserId());
        organizationAdditional.setCreatedAt(Timestamp.from(Instant.now()));

        organizationData.setOrganizationAdditional(organizationAdditional);

        organizationData = organizationDataRepository.save(organizationData);

        organizationManagerService.addManagerToOrganization(organizationData.getOrganizationId(), ManagerRequest.builder()
                        .managerId(authPrincipal.getUserId())
                        .email(authPrincipal.getEmail())
                .build());

        return OperationResponse.builder()
                .isSuccess(true)
                .entityId(organizationData.getOrganizationId())
                .build();
    }

    @Override
    public OperationResponse update(UUID organizationId, OrganizationRequest organizationRequest, UUID userId) {
        OrganizationData organizationData = organizationDataRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization data with id=%s was not found!".formatted(organizationId)));
        organizationDataMapper.toUpdatedEntity(organizationData, organizationRequest);

        OrganizationAdditional organizationAdditional = organizationData.getOrganizationAdditional();
        organizationAdditionalMapper.toUpdatedEntity(organizationAdditional, organizationRequest);

        organizationAdditional.setLastUpdateBy(userId);
        organizationAdditional.setLastUpdateAt(Timestamp.from(Instant.now()));

        organizationDataRepository.save(organizationData);
        return OperationResponse.builder()
                .isSuccess(true)
                .entityId(organizationData.getOrganizationId())
                .build();
    }

    @Override
    public OperationResponse delete(UUID organizationId, UUID userId) {
        organizationDataRepository.deleteById(organizationId);
        return OperationResponse.builder()
                .isSuccess(true)
                .build();
    }

    private Specification<OrganizationData> containsSearchRequest(String searchRequest) {
        return (root, query, criteriaBuilder) -> (searchRequest == null || searchRequest.isEmpty()) ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("searchRequest")),
                        "%" + searchRequest.toLowerCase() + "%");
    }
}
