package ru.meetpoint.eventservice.service;

import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.security.starter.response.OperationResponse;

import java.util.UUID;

public interface OrganizationManagerService {

    ManagerResponse getManagerInfo(UUID organizationId, UUID managerId);

    OperationResponse addManagerToOrganization(UUID organizationId, ManagerRequest managerRequest);

    OperationResponse deleteManagerFromOrganization(UUID organizationId, UUID managerId);
}
