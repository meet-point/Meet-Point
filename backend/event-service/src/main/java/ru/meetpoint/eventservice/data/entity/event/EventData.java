package ru.meetpoint.eventservice.data.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.eventservice.data.entity.category.CategoryData;
import ru.meetpoint.eventservice.data.entity.location.LocationData;
import ru.meetpoint.eventservice.data.enums.EventAccessStatus;
import ru.meetpoint.eventservice.data.enums.EventPublishStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_data")
public class EventData {

    @Id
    @UuidGenerator
    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "label")
    private String label;

    @Column(name = "preview")
    private String preview;

    @Column(name = "description")
    private String description;

    @Column(name = "date_time")
    private Timestamp dateTime;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "max_allowed_people")
    private int maxAllowedPeople;

    @Column(name = "registered_now")
    private int registeredNow;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_status")
    private EventAccessStatus accessStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "publish_status")
    private EventPublishStatus publishStatus;

    @OneToOne(mappedBy = "eventData", cascade = CascadeType.ALL, orphanRemoval = true)
    private EventAdditional eventAdditional;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private CategoryData categoryData;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private LocationData locationData;
}
