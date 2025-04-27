package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Integer userId);
}
