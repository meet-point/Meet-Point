package ru.meetpoint.eventservice.data.entity.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.meetpoint.eventservice.data.entity.event.EventData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_data")
public class CategoryData {

    @Id
    @UuidGenerator
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "label")
    private String label;

    @Column(name = "preview")
    private String preview;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "categoryData")
    private Set<EventData> events = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private CategoryType categoryType;

}
