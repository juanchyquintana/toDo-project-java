package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files, Integer> {
}
