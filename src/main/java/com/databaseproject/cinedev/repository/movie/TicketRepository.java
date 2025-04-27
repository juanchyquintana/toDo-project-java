package com.databaseproject.cinedev.repository.movie;

import com.databaseproject.cinedev.models.movie.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
