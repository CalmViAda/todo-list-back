package com.natixis.todoapp.adapters.apiweb.restapi;

import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;
import com.natixis.todoapp.adapters.apiweb.mapper.TaskDTOMapper;
import com.natixis.todoapp.domain.api.TaskUseCase;
import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import com.natixis.todoapp.domain.exception.TaskNotFound;
import com.natixis.todoapp.domain.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskUseCase taskUseCase;

    public TaskController(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) throws BadRequest {
        Task task = taskUseCase.addTask(TaskDTOMapper.fromRequestToDomain(taskRequest));
        TaskResponse taskResponse = TaskDTOMapper.toResponse(task);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam(defaultValue = "all") String filter) throws InvalidFilter {
        List<Task> tasks = taskUseCase.getTasksByFilter(filter);
        List<TaskResponse> taskResponses = tasks.stream()
                .map(TaskDTOMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable String id) throws TaskNotFound {
        Task task = taskUseCase.getTaskById(UUID.fromString(id));
        TaskResponse taskResponse = TaskDTOMapper.toResponse(task);
        return ResponseEntity.ok(taskResponse);
    }

}
