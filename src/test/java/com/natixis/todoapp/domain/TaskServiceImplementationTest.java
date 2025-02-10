package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import com.natixis.todoapp.domain.exception.TaskNotFound;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.domain.spi.TaskRepository;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
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

    @Test
    void add_task_should_throw_bad_request_when_label_is_null_or_empty() {
        Task taskWithNullLabel = TaskTestFactory.createTaskWithNullLabel();
        Task taskWithEmptyLabel = TaskTestFactory.createTaskWithEmptyLabel();

        assertThrows(BadRequest.class, () -> taskService.addTask(taskWithNullLabel));
        assertThrows(BadRequest.class, () -> taskService.addTask(taskWithEmptyLabel));
    }

    @Test
    void get_tasks_by_filter_should_return_all_tasks_when_filter_is_all() throws InvalidFilter {
        List<Task> allTasks = TaskTestFactory.createAllTasks();
        when(taskRepository.findAll()).thenReturn(allTasks);

        List<Task> actualTasks = taskService.getTasksByFilter("all");

        assertNotNull(actualTasks);
        assertEquals(allTasks.size(), actualTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void get_tasks_by_filter_should_return_incomplete_tasks_when_filter_is_status() throws InvalidFilter {
        List<Task> allTasks = TaskTestFactory.createAllTasks();
        when(taskRepository.findAll()).thenReturn(allTasks);

        List<Task> actualTasks = taskService.getTasksByFilter("status");

        assertNotNull(actualTasks);
        assertEquals(2, actualTasks.size());
        assertFalse(actualTasks.get(0).isComplete());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void get_tasks_by_filter_should_return_all_tasks_when_filter_is_unknown() throws InvalidFilter {
        String invalidFilter = "unknownFilter";

        assertThrows(InvalidFilter.class, () -> taskService.getTasksByFilter(invalidFilter));
    }

    @Test
    void get_tasks_by_filter_should_return_empty_list_when_no_incomplete_tasks() throws InvalidFilter {
        List<Task> allTasks = TaskTestFactory.createCompletedTasks();
        when(taskRepository.findAll()).thenReturn(allTasks);

        List<Task> actualTasks = taskService.getTasksByFilter("status");

        assertNotNull(actualTasks);
        assertTrue(actualTasks.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void get_task_by_id_should_return_task_when_task_exists() throws TaskNotFound {
        UUID taskId = UUID.randomUUID();
        Task expectedTask = TaskTestFactory.createTaskWithId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        Task actualTask = taskService.getTaskById(taskId);

        assertNotNull(actualTask);
        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getLabel(), actualTask.getLabel());
        assertEquals(expectedTask.isComplete(), actualTask.isComplete());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void get_task_by_id_should_throw_task_not_found_when_task_does_not_exist() {
        UUID nonExistentId = UUID.randomUUID();
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFound.class, () -> taskService.getTaskById(nonExistentId));
        verify(taskRepository, times(1)).findById(nonExistentId);
    }



}