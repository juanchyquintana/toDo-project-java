package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Integer> {
}