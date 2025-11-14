package ru.meetpoint.eventservice.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationAdditional;

import java.util.UUID;

public interface OrganizationAdditionalRepository extends JpaRepository<OrganizationAdditional, UUID>,
        PagingAndSortingRepository<OrganizationAdditional, UUID> {
}
