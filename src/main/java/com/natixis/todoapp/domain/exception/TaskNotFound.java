package com.natixis.todoapp.domain.exception;

import java.util.UUID;

public class TaskNotFound extends Exception {
    public TaskNotFound(UUID id) {
        super("No Task found for: "+ id.toString());
    }
}
