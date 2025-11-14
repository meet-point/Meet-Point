package ru.meetpoint.eventservice.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;

import java.util.UUID;

public interface OrganizationDataRepository extends JpaRepository<OrganizationData, UUID>,
        PagingAndSortingRepository<OrganizationData, UUID> {
}
