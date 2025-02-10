package com.natixis.todoapp.adapters.apiweb.mapper;

import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.domain.model.Task;

public class TaskDTOMapper {
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getLabel(), task.isComplete());
    }

    public static Task fromResponseToDomain(TaskResponse taskResponse) {
        return new Task(taskResponse.id(), taskResponse.label(), taskResponse.complete());
    }

    public static Task fromRequestToDomain(TaskRequest taskRequest) {
        return new Task(taskRequest.label(), taskRequest.complete());
    }
}
