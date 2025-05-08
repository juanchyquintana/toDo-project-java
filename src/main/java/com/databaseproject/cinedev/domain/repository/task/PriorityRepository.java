package com.databaseproject.cinedev.domain.repository.task;

import com.databaseproject.cinedev.domain.models.task.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}
