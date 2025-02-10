package com.natixis.todoapp.factory;

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
    public static TaskEntity createTaskEntityCompleted() {
        return new TaskEntity(
                "4fa85f64-5717-4562-b3fc-2c963f66afa6",
                "Lancer l'application back",
                true
        );
    }

    public static TaskEntity createTaskEntityUncompleted() {
        return new TaskEntity(
                "5fa85f64-5717-4562-b3fc-2c963f66afa6",
                "Partir en vacances",
                false
        );
    }

    public static TaskEntity createTaskEntityWithId(String taskId) {
        return new TaskEntity(
                taskId,
                "Partir en vacances",
                false
        );    }
}
