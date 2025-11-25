package ru.meetpoint.eventservice.data.entity.event;

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
@Table(name = "event_additional_info")
public class EventAdditional {

    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "publication_time")
    private Timestamp publicationDate;

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
    @JoinColumn(name = "event_id")
    private EventData eventData;
}
