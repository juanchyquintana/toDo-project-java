package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}
