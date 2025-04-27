package com.databaseproject.cinedev.repository.movie;

import com.databaseproject.cinedev.models.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
