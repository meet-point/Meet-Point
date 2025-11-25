package ru.meetpoint.eventservice.data.entity.location;

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
@Table(name = "location_additional_info")
public class LocationAdditional {

    @Id
    @Column(name = "location_id")
    private UUID locationId;

    @Column(name = "events_description")
    private String eventsDescription;

    @Column(name = "completed_events_description")
    private String completedEventsDescription;

    @Column(name = "organization_description")
    private String organizationDescription;

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
    @JoinColumn(name = "location_id")
    private LocationData locationData;
}
