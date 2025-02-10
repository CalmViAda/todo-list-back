package com.natixis.todoapp.adapters.persistance.entity;

import com.natixis.todoapp.domain.model.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    private String id;
    private String label;
    private boolean complete;

    public TaskEntity() {}

    public TaskEntity(String id, String label, boolean complete) {
        this.id = id;
        this.label = label;
        this.complete = complete;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isComplete() {
        return complete;
    }

}
