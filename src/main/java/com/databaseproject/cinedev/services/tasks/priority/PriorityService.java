package com.databaseproject.cinedev.services.tasks.priority;

import com.databaseproject.cinedev.models.task.Priority;
import com.databaseproject.cinedev.repository.task.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService implements IPriorityService {
    @Autowired
    PriorityRepository priorityRepository;

    @Override
    public void addPriority(Priority priority) {
        priorityRepository.save(priority);
    }

    @Override
    public void removePriority(Priority priority) {
        priorityRepository.delete(priority);
    }

    @Override
    public List<Priority> getAllPriority() {
        return priorityRepository.findAll();
    }
}
