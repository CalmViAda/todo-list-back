package com.natixis.todoapp.domain.api;

import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import com.natixis.todoapp.domain.model.Task;

import java.util.List;

public interface TaskUseCase {
    Task addTask(Task task) throws BadRequest;

    List<Task> getTasksByFilter(String filter) throws InvalidFilter;
}
