package com.natixis.todoapp.adapters.persistance.mapper;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.factory.TaskEntityTestFactory;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskEntityMapperTest {

    @Test
    void to_entity_should_return_task_entity_when_convert_task_to_task_entity() {
        Task expectedTask = TaskTestFactory.createTask();

        TaskEntity actualTaskEntity = TaskEntityMapper.toEntity(expectedTask);

        assertNotNull(actualTaskEntity);
        assertEquals(expectedTask.getId(), UUID.fromString(actualTaskEntity.getId()));
        assertEquals(expectedTask.getLabel(), actualTaskEntity.getLabel());
        assertEquals(expectedTask.isComplete(), actualTaskEntity.isComplete());
    }

    @Test
    void to_domain_should_return_task_when_convert_task_entity_to_task() {
        TaskEntity taskEntity = TaskEntityTestFactory.createTaskEntity();

        Task task = TaskEntityMapper.toDomain(taskEntity);

        assertNotNull(task);
        assertEquals(UUID.fromString(taskEntity.getId()), task.getId());
        assertEquals(taskEntity.getLabel(), task.getLabel());
        assertEquals(taskEntity.isComplete(), task.isComplete());
    }
}