package com.natixis.todoapp.adapters.persistance;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import com.natixis.todoapp.adapters.persistance.jpa.TaskJpaRepository;
import com.natixis.todoapp.adapters.persistance.mapper.TaskEntityMapper;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<Task> findAll() {
        return taskJpaRepository.findAll().stream()
                .map(TaskEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return taskJpaRepository.findById(id.toString()).map(TaskEntityMapper::toDomain);
    }
}
