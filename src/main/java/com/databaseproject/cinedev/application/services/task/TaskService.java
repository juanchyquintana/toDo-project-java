package com.databaseproject.cinedev.application.services.task;

import com.databaseproject.cinedev.domain.models.task.Task;
import com.databaseproject.cinedev.domain.models.base.User;
import com.databaseproject.cinedev.domain.repository.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(User user) {
        return taskRepository.findAllByUserId(user.getId());
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}
