package ru.meetpoint.eventservice.data.entity.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_type")
public class CategoryType {

    @Id
    @UuidGenerator
    @Column(name = "type_id")
    UUID type_id;

    @Column(name = "label")
    String label;

    @Column(name = "preview")
    String preview;

    @Column(name = "description")
    String description;

    @Builder.Default
    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryData> credentials = new HashSet<>();
}
