package ru.meetpoint.eventservice.data.entity.organization;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_additional_info")
public class OrganizationAdditional {

    @Id
    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "events_description")
    private String eventsDescription;

    @Column(name = "completed_events_description")
    private String completedEventsDescription;

    @Column(name = "locations_description")
    private String locationsDescription;

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_update_by")
    private UUID lastUpdateBy;

    @Column(name = "last_update_at")
    private Timestamp lastUpdateAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "organization_id")
    private OrganizationData organizationData;

}
