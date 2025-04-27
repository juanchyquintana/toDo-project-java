package com.databaseproject.cinedev.models.movie;

import com.databaseproject.cinedev.models.base.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    private LocalDateTime datePurchase;

    @ManyToOne
    @JoinColumn(name = "chair_id", nullable = false)
    private Chairs chairs;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "function_id", nullable = false)
    private Functions functions;


    public Ticket(Double price, LocalDateTime datePurchase, User user) {
        this.price = price;
        this.datePurchase = datePurchase;
        this.user = user;
    }

    @Override
    public String toString() {
        String movieTitle = functions != null && functions.getMovie() != null
                ? functions.getMovie().getTitle()
                : "Unknown Movie";

        String roomName = functions != null && functions.getRoom() != null
                ? functions.getRoom().getName()
                : "Unknown Room";

        String time = functions != null && functions.getStartTime() != null
                ? functions.getStartTime().toString()
                : "Unknown Time";

        String seat = chairs != null ? chairs.getNumber() : "Unknown Seat";

        return String.format(
                "🎬 %s | 🛋 Room: %s | ⏰ %s | 💺 Seat: %s | 💵 $%.2f",
                movieTitle, roomName, time, seat, price != null ? price : 0.0
        );
    }

}
