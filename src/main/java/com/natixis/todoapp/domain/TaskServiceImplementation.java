package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.api.TaskUseCase;
import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskUseCase {
    private final TaskRepository taskRepository;

    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(Task task) throws BadRequest {
        if (task.getLabel() == null || task.getLabel().length() == 0) {
            throw new BadRequest();
        }
        return taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks", key = "#filter")
    public List<Task> getTasksByFilter(String filter) throws InvalidFilter {
        Optional<TaskFilterStrategy> optionalTaskFilterStrategy = Optional.ofNullable(TaskFilterStrategy.fromString(filter));
        TaskFilterStrategy taskFilterStrategy = optionalTaskFilterStrategy.orElseThrow(()-> new InvalidFilter(filter));
        List<Task> tasks = taskRepository.findAll();
        return taskFilterStrategy.filter(tasks);
    }
}
