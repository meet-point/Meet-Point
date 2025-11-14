package ru.meetpoint.eventservice.data.entity.location;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.eventservice.data.entity.event.EventData;
import ru.meetpoint.eventservice.data.entity.organization.OrganizationData;
import ru.meetpoint.eventservice.data.enums.LocationPublicStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location_data")
public class LocationData {

    @Id
    @UuidGenerator
    @Column(name = "location_id")
    private UUID locationId;

    @Column(name = "label")
    private String label;

    @Column(name = "preview")
    private String preview;

    @Column(name = "description")
    private String description;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "public_status")
    private LocationPublicStatus publicStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private LocationAdditional locationAdditional;

    @Builder.Default
    @OneToMany(mappedBy = "locationData")
    private Set<EventData> eventData = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private OrganizationData organizationData;
}
