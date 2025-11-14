package ru.meetpoint.eventservice.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationManager;
import ru.meetpoint.eventservice.data.entity.organization.embeddable.OrganizationManagerId;

public interface OrganizationManagerRepository extends JpaRepository<OrganizationManager, OrganizationManagerId>,
        PagingAndSortingRepository<OrganizationManager, OrganizationManagerId> {
}
