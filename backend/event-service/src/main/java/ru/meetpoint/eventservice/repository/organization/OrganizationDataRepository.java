package ru.meetpoint.eventservice.repository.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;

import java.util.List;
import java.util.UUID;

public interface OrganizationDataRepository extends JpaRepository<OrganizationData, UUID>,
        JpaSpecificationExecutor<OrganizationData>, PagingAndSortingRepository<OrganizationData, UUID> {

    @Query(value = """
        SELECT * FROM organization_data
""", countQuery = """
        SELECT COUNT(*) FROM organization_data
""", nativeQuery = true)
    Page<OrganizationData> findAllPageable(Pageable pageable);

    @Query(value = """
            SELECT * FROM organization_data
            ORDER BY label
            LIMIT 20 OFFSET 0;
    """, nativeQuery = true)
    List<OrganizationData> findForMainPage();
}
