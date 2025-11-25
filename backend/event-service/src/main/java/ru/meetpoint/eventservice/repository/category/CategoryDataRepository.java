package ru.meetpoint.eventservice.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.meetpoint.eventservice.data.entity.category.CategoryData;

import java.util.UUID;

public interface CategoryDataRepository extends JpaRepository<CategoryData, UUID> {
}
