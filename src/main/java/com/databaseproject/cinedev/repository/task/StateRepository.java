package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.task.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {
    Optional<State> findByName(String name);
}
