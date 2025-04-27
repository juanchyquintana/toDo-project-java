package com.databaseproject.cinedev.models.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Files {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private String url;
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
