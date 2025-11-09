package ru.meetpoint.eventservice.data.entity.organization.embadable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationManagerId implements Serializable {

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "manager_id")
    private UUID managerId;
}
