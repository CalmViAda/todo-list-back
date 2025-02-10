package com.natixis.todoapp.adapters.apiweb.mapper;

import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.factory.TaskRequestTestFactory;
import com.natixis.todoapp.factory.TaskResponseTestFactory;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOMapperTest {

    @Test
    void to_response_should_return_task_response_when_convert_task_to_task_response() {
        Task expectedTask = TaskTestFactory.createTask();

        TaskResponse actualTaskResponse = TaskDTOMapper.toResponse(expectedTask);

        assertNotNull(actualTaskResponse);
        assertEquals(expectedTask.getId(), actualTaskResponse.id());
        assertEquals(expectedTask.getLabel(), actualTaskResponse.label());
        assertEquals(expectedTask.isComplete(), actualTaskResponse.complete());
    }

    @Test
    void from_response_to_domain_should_return_task_when_convert_task_response_to_task() {
        TaskResponse expectedTaskResponse = TaskResponseTestFactory.createTaskResponse();

        Task actualTask = TaskDTOMapper.fromResponseToDomain(expectedTaskResponse);

        assertNotNull(actualTask);
        assertEquals(expectedTaskResponse.id(), actualTask.getId());
        assertEquals(expectedTaskResponse.label(), actualTask.getLabel());
        assertEquals(expectedTaskResponse.complete(), actualTask.isComplete());
    }

    @Test
    void from_request_to_domain_should_return_task_when_convert_task_request_to_task() {
        TaskRequest expectedTaskRequest = TaskRequestTestFactory.createTaskRequest();

        Task actualTask = TaskDTOMapper.fromRequestToDomain(expectedTaskRequest);

        assertNotNull(actualTask);
        assertEquals(expectedTaskRequest.label(), actualTask.getLabel());
        assertEquals(expectedTaskRequest.complete(), actualTask.isComplete());
    }
}