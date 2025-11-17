package ru.meetpoint.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.eventservice.data.dto.response.operation.OperationResponse;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationManager;
import ru.meetpoint.eventservice.exception.NotFoundException;
import ru.meetpoint.eventservice.mapper.OrganizationManagerMapper;
import ru.meetpoint.eventservice.repository.organization.OrganizationDataRepository;
import ru.meetpoint.eventservice.repository.organization.OrganizationManagerRepository;
import ru.meetpoint.eventservice.service.OrganizationManagerService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationManagerServiceImpl implements OrganizationManagerService {

    private final OrganizationDataRepository organizationDataRepository;

    private final OrganizationManagerRepository organizationManagerRepository;

    private final OrganizationManagerMapper organizationManagerMapper;

    @Override
    public ManagerResponse getManagerInfo(UUID organizationId, UUID managerId) {
        return organizationManagerMapper.toResponse(organizationManagerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException("Manager with id=%s was not found at this organization!".formatted(managerId))));
    }

    @Override
    @Transactional
    public OperationResponse addManagerToOrganization(UUID organizationId, ManagerRequest managerRequest) {
        OrganizationManager organizationManager = organizationManagerMapper.toEntity(managerRequest);

        OrganizationData organizationData = organizationDataRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization data with id=%s was not found!".formatted(organizationId)));

        organizationManager.setOrganizationData(organizationData);
        organizationManager.setDateTime(Timestamp.from(Instant.now()));

        organizationManagerRepository.save(organizationManager);

        return OperationResponse.builder()
                .isSuccess(true)
                .entityId(managerRequest.managerId())
                .build();
    }

    @Override
    public OperationResponse deleteManagerFromOrganization(UUID organizationId, UUID managerId) {
        organizationManagerRepository.deleteById(managerId);

        return OperationResponse.builder()
                .isSuccess(true).build();
    }
}
