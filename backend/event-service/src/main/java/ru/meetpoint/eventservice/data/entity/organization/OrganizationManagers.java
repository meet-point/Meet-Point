package ru.meetpoint.eventservice.data.entity.organization;

import jakarta.persistence.*;
import lombok.*;
import ru.meetpoint.eventservice.data.entity.organization.embadable.OrganizationManagerId;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_manager")
public class OrganizationManagers {

    @EmbeddedId
    private OrganizationManagerId organizationManagerId;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @Column(name = "date_time", nullable = false)
    private Timestamp dateTime;

    @ManyToOne
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id")
    private OrganizationData organization;
}
