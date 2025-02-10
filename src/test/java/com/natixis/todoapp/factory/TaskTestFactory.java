package com.natixis.todoapp.factory;

import com.natixis.todoapp.domain.model.Task;

import java.util.UUID;

public class TaskTestFactory {
    public static Task createTask() {
        return new Task(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                "Finir l'application back",
                false
        );
    }
}
