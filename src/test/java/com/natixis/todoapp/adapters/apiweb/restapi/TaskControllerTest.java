package com.natixis.todoapp.adapters.apiweb.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.domain.api.TaskUseCase;
import com.natixis.todoapp.domain.exception.BadRequest;
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
}