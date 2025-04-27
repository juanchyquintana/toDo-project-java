package com.databaseproject.cinedev.services.tasks.state;

import com.databaseproject.cinedev.models.task.State;
import com.databaseproject.cinedev.repository.task.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService implements IStateService {
    @Autowired
    StateRepository stateRepository;

    @Override
    public void addState(State state) {
        stateRepository.save(state);
    }

    @Override
    public void removeState(State state) {
        stateRepository.delete(state);
    }

    @Override
    public List<State> getAllState() {
        return stateRepository.findAll();
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByName(name).orElse(null);
    }
}
