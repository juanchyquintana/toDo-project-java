package com.databaseproject.cinedev.models.task;

import com.databaseproject.cinedev.models.base.compositeKey.TaskTagId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagsTask {
    @EmbeddedId
    private TaskTagId id = new TaskTagId();

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tags tags;
}
