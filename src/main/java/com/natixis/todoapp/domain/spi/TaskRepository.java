package com.natixis.todoapp.domain.spi;

import com.natixis.todoapp.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    Task save(Task task);

    List<Task> findAll();

    Optional<Task> findById(UUID id);

    void delete(Task task);
}
