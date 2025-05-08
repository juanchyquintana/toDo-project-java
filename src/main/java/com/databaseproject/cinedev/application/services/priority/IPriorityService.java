package com.databaseproject.cinedev.application.services.priority;

import com.databaseproject.cinedev.domain.models.task.Priority;

import java.util.List;

public interface IPriorityService {
    void addPriority(Priority priority);

    void removePriority(Priority priority);

    List<Priority> getAllPriority();
}
