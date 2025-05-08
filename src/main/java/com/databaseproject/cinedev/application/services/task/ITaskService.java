package com.databaseproject.cinedev.application.services.task;

import com.databaseproject.cinedev.domain.models.task.Task;
import com.databaseproject.cinedev.domain.models.base.User;

import java.util.List;

public interface ITaskService {
    public void saveTask(Task task);

    public List<Task> getTaskByUserId(User user);

    public void deleteTask(Task task);
}
