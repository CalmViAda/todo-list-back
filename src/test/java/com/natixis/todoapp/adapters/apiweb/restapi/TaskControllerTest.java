package com.natixis.todoapp.adapters.apiweb.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.adapters.apiweb.mapper.TaskDTOMapper;
import com.natixis.todoapp.domain.api.TaskUseCase;
import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import com.natixis.todoapp.domain.exception.TaskNotFound;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.factory.TaskRequestTestFactory;
import com.natixis.todoapp.factory.TaskResponseTestFactory;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskUseCase taskUseCase;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new TaskExceptionHandler())
                .build();
    }

    @Test
    void create_task_should_return_task_response_when_task_is_created() throws Exception {
        TaskRequest givenTaskRequest = TaskRequestTestFactory.createTaskRequest();
        Task expectedTask = TaskTestFactory.createTask();
        TaskResponse expectedTaskResponse = TaskResponseTestFactory.createTaskResponse();

        when(taskUseCase.addTask(any(Task.class))).thenReturn(expectedTask);

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(givenTaskRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedTaskResponse.id().toString()))
                .andExpect(jsonPath("$.label").value(expectedTaskResponse.label()))
                .andExpect(jsonPath("$.complete").value(expectedTaskResponse.complete()));
        verify(taskUseCase, times(1)).addTask(any(Task.class));
    }

    @Test
    void create_task_should_return_bad_request_when_task_request_is_invalid() throws Exception {
        TaskRequest givenTaskRequest = TaskRequestTestFactory.createBadTaskRequest();
        when(taskUseCase.addTask(any(Task.class))).thenThrow(new BadRequest());

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(givenTaskRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Request is not correct !"));
        verify(taskUseCase, times(1)).addTask(any(Task.class));
    }

    @Test
    void create_task_should_return_internal_server_when_throw_unknown_exception() throws Exception {
        TaskRequest givenTaskRequest = TaskRequestTestFactory.createTaskRequest();
        when(taskUseCase.addTask(any(Task.class))).thenThrow(new RuntimeException("Database Error!"));

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(givenTaskRequest)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors[0]").value("Database Error!"));
        verify(taskUseCase, times(1)).addTask(any(Task.class));
    }

    @Test
    void get_tasks_should_return_all_tasks_when_filter_is_all() throws Exception {
        List<Task> tasks = List.of(
                TaskTestFactory.createTask(),
                TaskTestFactory.createTaskCompleted(),
                TaskTestFactory.createTaskUncompleted()
        );

        List<TaskResponse> expectedResponses = tasks.stream()
                .map(TaskDTOMapper::toResponse)
                .toList();

        when(taskUseCase.getTasksByFilter("all")).thenReturn(tasks);

        mockMvc.perform(get("/api/task")
                        .param("filter", "all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResponses.size()))
                .andExpect(jsonPath("$[0].id").value(expectedResponses.get(0).id().toString()))
                .andExpect(jsonPath("$[0].label").value(expectedResponses.get(0).label()))
                .andExpect(jsonPath("$[0].complete").value(expectedResponses.get(0).complete()));
        verify(taskUseCase, times(1)).getTasksByFilter("all");
    }

    @Test
    void get_tasks_should_return_only_incomplete_tasks_when_filter_is_status() throws Exception {
        List<Task> incompleteTasks = List.of(
                TaskTestFactory.createTask(),
                TaskTestFactory.createTaskUncompleted()
        );

        List<TaskResponse> expectedResponses = incompleteTasks.stream()
                .map(TaskDTOMapper::toResponse)
                .toList();

        when(taskUseCase.getTasksByFilter("status")).thenReturn(incompleteTasks);

        mockMvc.perform(get("/api/task")
                        .param("filter", "status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResponses.size()))
                .andExpect(jsonPath("$[0].id").value(expectedResponses.get(0).id().toString()))
                .andExpect(jsonPath("$[0].label").value(expectedResponses.get(0).label()))
                .andExpect(jsonPath("$[0].complete").value(expectedResponses.get(0).complete()));
        verify(taskUseCase, times(1)).getTasksByFilter("status");
    }

    @Test
    void get_tasks_should_return_bad_request_error_when_invalid_filter_exception_occurs() throws Exception {
        when(taskUseCase.getTasksByFilter(anyString())).thenThrow(new InvalidFilter("unknown"));

        mockMvc.perform(get("/api/task")
                        .param("filter", "unknown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Invalid filter: unknown"));
        verify(taskUseCase, times(1)).getTasksByFilter("unknown");
    }

    @Test
    void get_tasks_should_return_internal_server_error_when_exception_occurs() throws Exception {
        when(taskUseCase.getTasksByFilter(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/task")
                        .param("filter", "all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error"));
        verify(taskUseCase, times(1)).getTasksByFilter("all");
    }

    @Test
    void get_task_by_id_should_return_task_response_when_task_exists() throws Exception {
        Task expectedTask = TaskTestFactory.createTask();
        TaskResponse expectedTaskResponse = TaskResponseTestFactory.createTaskResponse();
        when(taskUseCase.getTaskById(expectedTask.getId())).thenReturn(expectedTask);

        mockMvc.perform(get("/api/task/{id}", expectedTask.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedTaskResponse.id().toString()))
                .andExpect(jsonPath("$.label").value(expectedTaskResponse.label()))
                .andExpect(jsonPath("$.complete").value(expectedTaskResponse.complete()));
        verify(taskUseCase, times(1)).getTaskById(expectedTask.getId());
    }

    @Test
    void get_task_by_id_should_return_not_found_when_task_does_not_exist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        when(taskUseCase.getTaskById(nonExistentId)).thenThrow(new TaskNotFound(nonExistentId));

        mockMvc.perform(get("/api/task/{id}", nonExistentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]").value("No Task found for: "+nonExistentId));
        verify(taskUseCase, times(1)).getTaskById(nonExistentId);
    }

    @Test
    void get_task_by_id_should_return_internal_server_error_when_exception_occurs() throws Exception {
        UUID validId = UUID.randomUUID();
        when(taskUseCase.getTaskById(validId)).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/task/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors[0]").value("Unexpected error"));
        verify(taskUseCase, times(1)).getTaskById(validId);
    }

    @Test
    void update_task_status_should_return_updated_task() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskResponse expectedResponse = TaskResponseTestFactory.createTaskResponseWithStatus(true);

        when(taskUseCase.updateTaskStatus(taskId, true)).thenReturn(TaskTestFactory.createTaskWithStatus(true));

        mockMvc.perform(patch("/api/task/{id}/status", taskId)
                        .param("complete", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complete").value(true));

        verify(taskUseCase, times(1)).updateTaskStatus(taskId, true);
    }

    @Test
    void update_task_status_should_return_not_found_when_task_does_not_exist() throws Exception {
        UUID taskId = UUID.randomUUID();

        when(taskUseCase.updateTaskStatus(taskId, true)).thenThrow(new TaskNotFound(taskId));

        mockMvc.perform(patch("/api/task/{id}/status", taskId)
                        .param("complete", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(taskUseCase, times(1)).updateTaskStatus(taskId, true);
    }


}