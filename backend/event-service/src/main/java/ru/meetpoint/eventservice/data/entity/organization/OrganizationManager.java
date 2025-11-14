package ru.meetpoint.eventservice.data.entity.organization;

import jakarta.persistence.*;
import lombok.*;
import ru.meetpoint.eventservice.data.entity.organization.embeddable.OrganizationManagerId;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_manager")
public class OrganizationManager {

    @EmbeddedId
    private OrganizationManagerId organizationManagerId;

    @Column(name = "date_time", nullable = false)
    private Timestamp dateTime;

    @ManyToOne
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id")
    private OrganizationData organizationData;
}
