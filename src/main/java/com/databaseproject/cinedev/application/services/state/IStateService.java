package com.databaseproject.cinedev.application.services.state;

import com.databaseproject.cinedev.domain.models.task.State;

import java.util.List;

public interface IStateService {
    void addState(State state);

    void removeState(State state);

    List<State> getAllState();

    State getByName(String name);
}
