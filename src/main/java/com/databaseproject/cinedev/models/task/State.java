package com.databaseproject.cinedev.models.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private State fatherState;

    @OneToMany(mappedBy = "fatherState")
    private List<State> sonState;

    @Override
    public String toString() {
        return name;
    }
}
