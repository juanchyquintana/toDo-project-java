package com.databaseproject.cinedev.services.tasks.priority;

import com.databaseproject.cinedev.models.task.Priority;

import java.util.List;

public interface IPriorityService {
    void addPriority(Priority priority);

    void removePriority(Priority priority);

    List<Priority> getAllPriority();
}
