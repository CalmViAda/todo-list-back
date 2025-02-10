package com.natixis.todoapp.domain.spi;

import com.natixis.todoapp.domain.model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    List<Task> findAll();
}
