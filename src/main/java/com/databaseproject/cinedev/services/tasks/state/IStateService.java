package com.databaseproject.cinedev.services.tasks.state;

import com.databaseproject.cinedev.models.task.State;

import java.util.List;

public interface IStateService {
    void addState(State state);

    void removeState(State state);

    List<State> getAllState();

    State getByName(String name);
}
