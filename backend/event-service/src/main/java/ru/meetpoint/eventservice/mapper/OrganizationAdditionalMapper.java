package ru.meetpoint.eventservice.mapper;

import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.organization.OrganizationRequest;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationAdditional;

@Component
public class OrganizationAdditionalMapper {

    public OrganizationAdditional toEntity(OrganizationRequest request) {
        return OrganizationAdditional.builder()
                .contactEmail(request.contactEmail())
                .contactPhone(request.contactPhone())
                .eventsDescription(request.eventsDescription())
                .completedEventsDescription(request.completedEventsDescription())
                .locationsDescription(request.locationsDescription())
                .build();
    }

    public void toUpdatedEntity(OrganizationAdditional organizationAdditional, OrganizationRequest organizationRequest) {
        if (organizationRequest.contactEmail() != null && !organizationRequest.contactEmail().isEmpty()) {
            organizationAdditional.setContactEmail((organizationRequest.contactEmail()));
        }

        if (organizationRequest.eventsDescription() != null && !organizationRequest.eventsDescription().isEmpty()) {
            organizationAdditional.setEventsDescription(organizationRequest.eventsDescription());
        }

        if (organizationRequest.eventsDescription() != null && !organizationRequest.eventsDescription().isEmpty()) {
            organizationAdditional.setEventsDescription(organizationRequest.eventsDescription());
        }

        if (organizationRequest.completedEventsDescription() != null &&
                !organizationRequest.completedEventsDescription().isEmpty()) {
            organizationAdditional.setCompletedEventsDescription(organizationRequest.completedEventsDescription());
        }

        if (organizationRequest.locationsDescription() != null && !organizationRequest.locationsDescription().isEmpty()) {
            organizationAdditional.setLocationsDescription(organizationRequest.locationsDescription());
        }
    }
}
