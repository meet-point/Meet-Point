package ru.meetpoint.eventservice.data.entity.organization;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.eventservice.data.entity.location.LocationData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization_data")
public class OrganizationData {

    @Id
    @UuidGenerator
    @Column(name = "organization_id")
    private UUID organizationId;

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

    @OneToOne(cascade = CascadeType.ALL)
    private OrganizationAdditional organizationAdditional;

    @OneToMany(mappedBy = "organizationData")
    private Set<OrganizationManager> managers = new HashSet<>();

    @OneToMany(mappedBy = "organizationData")
    private Set<LocationData> locations = new HashSet<>();
}
