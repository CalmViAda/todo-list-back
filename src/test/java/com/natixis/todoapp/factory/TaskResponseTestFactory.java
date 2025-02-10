package com.natixis.todoapp.factory;

import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;

import java.util.UUID;

public class TaskResponseTestFactory {
    public static TaskResponse createTaskResponse() {
        return new TaskResponse(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                "Finir l'application back",
                false
        );
    }
}
