package com.natixis.todoapp.domain.spi;

import com.natixis.todoapp.domain.model.Task;

public interface TaskRepository {
    Task save(Task task);
}
