package com.natixis.todoapp.domain.api;

import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.model.Task;

public interface TaskUseCase {
    Task addTask(Task task) throws BadRequest;
}
