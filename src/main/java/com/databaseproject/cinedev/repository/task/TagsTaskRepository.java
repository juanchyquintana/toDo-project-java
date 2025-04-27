package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.TagsTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsTaskRepository extends JpaRepository<TagsTask, Integer> {
}
