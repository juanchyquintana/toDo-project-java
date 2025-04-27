package com.databaseproject.cinedev.services.tasks.task;

import com.databaseproject.cinedev.models.task.Task;
import com.databaseproject.cinedev.models.base.User;

import java.util.List;

public interface ITaskService {
    public void saveTask(Task task);

    public List<Task> getTaskByUserId(User user);

    public void deleteTask(Task task);
}
