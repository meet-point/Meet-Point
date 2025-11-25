package ru.meetpoint.eventservice.data.entity.organization;

import jakarta.persistence.*;
import lombok.*;

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

    @Id
    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "email")
    private String email;

    @Column(name = "date_time", nullable = false)
    private Timestamp dateTime;

    @OneToOne
    @JoinColumn(name = "organization_id")
    private OrganizationData organizationData;
}
