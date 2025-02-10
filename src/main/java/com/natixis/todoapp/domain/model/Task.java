package com.natixis.todoapp.domain.model;

import java.util.UUID;

public class Task {
    private final UUID id;
    private final String label;
    private final boolean complete;

    public Task(UUID id, String label, boolean complete) {
        this.id = id;
        this.label = label;
        this.complete = complete;
    }

    public Task(String label, boolean complete) {
        this.id = UUID.randomUUID();
        this.label = label;
        this.complete = complete;
    }

    public UUID getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isComplete() {
        return complete;
    }

}
