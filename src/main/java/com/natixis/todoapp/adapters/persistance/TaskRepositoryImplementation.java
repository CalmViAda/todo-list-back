package com.natixis.todoapp.adapters.persistance;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import com.natixis.todoapp.adapters.persistance.jpa.TaskJpaRepository;
import com.natixis.todoapp.adapters.persistance.mapper.TaskEntityMapper;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImplementation implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    public TaskRepositoryImplementation(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity savedTaskEntity = taskJpaRepository.save(TaskEntityMapper.toEntity(task));
        return TaskEntityMapper.toDomain(savedTaskEntity);
    }
}
