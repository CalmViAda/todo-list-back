package com.natixis.todoapp.adapters.persistance.mapper;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import com.natixis.todoapp.domain.model.Task;

import java.util.UUID;

public class TaskEntityMapper {
    public static TaskEntity toEntity(Task task) {
        return new TaskEntity(task.getId().toString(), task.getLabel(), task.isComplete());
    }

    public static Task toDomain(TaskEntity entity) {
        return new Task(UUID.fromString(entity.getId()), entity.getLabel(), entity.isComplete());
    }
}
