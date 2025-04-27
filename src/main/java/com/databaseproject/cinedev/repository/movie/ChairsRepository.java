package com.databaseproject.cinedev.repository.movie;

import com.databaseproject.cinedev.models.movie.Chairs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairsRepository extends JpaRepository<Chairs, Integer> {
}
