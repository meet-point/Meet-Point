package ru.meetpoint.eventservice.repository.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationManager;

import java.util.UUID;

public interface OrganizationManagerRepository extends JpaRepository<OrganizationManager, UUID>,
        PagingAndSortingRepository<OrganizationManager, UUID> {

    @Query(value = """
        SELECT * FROM organization_manager
""", countQuery = """
        SELECT COUNT(*) FROM organization_manager
""", nativeQuery = true)
    Page<OrganizationManager> findAllPageable(Pageable pageable);
}
