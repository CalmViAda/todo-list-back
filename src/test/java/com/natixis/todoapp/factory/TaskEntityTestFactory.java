package com.natixis.todoapp.factory;

import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;

import java.util.UUID;

public class TaskEntityTestFactory {
    public static TaskEntity createTaskEntity() {
        return new TaskEntity(
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "Finir l'application back",
                false
        );
    }
}
