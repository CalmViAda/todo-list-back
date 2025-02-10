package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.api.TaskUseCase;
import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import org.springframework.stereotype.Service;

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
}
