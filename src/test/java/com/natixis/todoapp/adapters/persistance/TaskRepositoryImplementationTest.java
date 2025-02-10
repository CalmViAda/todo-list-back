package com.natixis.todoapp.adapters.persistance;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import com.natixis.todoapp.adapters.persistance.jpa.TaskJpaRepository;
import com.natixis.todoapp.adapters.persistance.mapper.TaskEntityMapper;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.factory.TaskEntityTestFactory;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskRepositoryImplementationTest {
    @Mock
    private TaskJpaRepository taskJpaRepository;

    @InjectMocks
    private TaskRepositoryImplementation taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_should_return_task_when_jpa_save_succeed() {
        Task expectedTask = TaskTestFactory.createTask();
        TaskEntity expectedTaskEntity = TaskEntityMapper.toEntity(expectedTask);
        when(taskJpaRepository.save(any(TaskEntity.class))).thenReturn(expectedTaskEntity);

        Task actualTask = taskRepository.save(expectedTask);

        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getLabel(), actualTask.getLabel());
        assertEquals(expectedTask.isComplete(), actualTask.isComplete());
        verify(taskJpaRepository, times(1)).save(any(TaskEntity.class));
    }


    @Test
    void save_should_throw_exception_when_jpa_save_fails() {
        Task task = TaskTestFactory.createTask();
        when(taskJpaRepository.save(any(TaskEntity.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskRepository.save(task);
        });

        assertEquals("Database error", exception.getMessage());
        verify(taskJpaRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void find_all_should_return_list_of_tasks_when_jpa_returns_entities() {
        List<TaskEntity> taskEntities = List.of(
                TaskEntityTestFactory.createTaskEntity(),
                TaskEntityTestFactory.createTaskEntityCompleted(),
                TaskEntityTestFactory.createTaskEntityUncompleted()
        );
        when(taskJpaRepository.findAll()).thenReturn(taskEntities);

        List<Task> expectedTasks = taskEntities.stream()
                .map(TaskEntityMapper::toDomain)
                .toList();

        List<Task> actualTasks = taskRepository.findAll();

        assertEquals(expectedTasks.size(), actualTasks.size());
        assertEquals(expectedTasks.get(0).getLabel(), actualTasks.get(0).getLabel());
        assertEquals(expectedTasks.get(1).isComplete(), actualTasks.get(1).isComplete());
        verify(taskJpaRepository, times(1)).findAll();
    }

    @Test
    void find_all_should_return_empty_list_when_jpa_returns_empty() {
        when(taskJpaRepository.findAll()).thenReturn(List.of());

        List<Task> actualTasks = taskRepository.findAll();

        assertTrue(actualTasks.isEmpty());
        verify(taskJpaRepository, times(1)).findAll();
    }

    @Test
    void find_all_should_throw_exception_when_jpa_fails() {
        when(taskJpaRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskRepository.findAll();
        });

        assertEquals("Database error", exception.getMessage());
        verify(taskJpaRepository, times(1)).findAll();
    }

    @Test
    void find_by_id_should_return_task_when_task_exists() {
        String taskId = UUID.randomUUID().toString();
        TaskEntity taskEntity = TaskEntityTestFactory.createTaskEntityWithId(taskId);
        Task expectedTask = TaskEntityMapper.toDomain(taskEntity);
        when(taskJpaRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        Optional<Task> actualTask = taskRepository.findById(UUID.fromString(taskId));

        assertTrue(actualTask.isPresent());
        assertEquals(expectedTask.getId(), actualTask.get().getId());
        assertEquals(expectedTask.getLabel(), actualTask.get().getLabel());
        assertEquals(expectedTask.isComplete(), actualTask.get().isComplete());
        verify(taskJpaRepository, times(1)).findById(taskId);
    }

    @Test
    void find_by_id_should_return_empty_when_task_does_not_exist() {
        String nonExistentId = UUID.randomUUID().toString();
        when(taskJpaRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<Task> actualTask = taskRepository.findById(UUID.fromString(nonExistentId));

        assertTrue(actualTask.isEmpty());
        verify(taskJpaRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void find_by_id_should_throw_exception_when_jpa_fails() {
        String taskId = UUID.randomUUID().toString();
        when(taskJpaRepository.findById(taskId)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskRepository.findById(UUID.fromString(taskId));
        });

        assertEquals("Database error", exception.getMessage());
        verify(taskJpaRepository, times(1)).findById(taskId);
    }

}