package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplementationTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImplementation taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_task_should_return_task_when_save_succeed() throws BadRequest {
        Task expectedTask = TaskTestFactory.createTask();
        when(taskRepository.save(expectedTask)).thenReturn(expectedTask);

        Task actualTask = taskService.addTask(expectedTask);

        assertNotNull(actualTask);
        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getLabel(), actualTask.getLabel());
        assertEquals(expectedTask.isComplete(), actualTask.isComplete());

        verify(taskRepository, times(1)).save(expectedTask);
    }
}