package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
