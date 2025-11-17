package ru.meetpoint.eventservice.mapper;

import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.manage.ManagerRequest;
import ru.meetpoint.eventservice.data.dto.response.manage.ManagerResponse;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationManager;

@Component
public class OrganizationManagerMapper {

    public ManagerResponse toResponse(OrganizationManager organizationManager) {
        return ManagerResponse.builder()
                .managerId(organizationManager.getManagerId())
                .email(organizationManager.getEmail())
                .build();
    }

    public OrganizationManager toEntity(ManagerRequest managerRequest) {
        return OrganizationManager.builder()
                .managerId(managerRequest.managerId())
                .email(managerRequest.email())
                .build();
    }
}
