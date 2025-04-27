package com.databaseproject.cinedev.repository.movie;

import com.databaseproject.cinedev.models.movie.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
}
