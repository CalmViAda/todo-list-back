package com.natixis.todoapp.factory;

import com.natixis.todoapp.adapters.apiweb.dto.TaskRequest;
import com.natixis.todoapp.adapters.apiweb.dto.TaskResponse;

import java.util.UUID;

public class TaskRequestTestFactory {
    public static TaskRequest createTaskRequest() {
        return new TaskRequest(
                "Finir l'application back",
                false
        );
    }

    public static TaskRequest createBadTaskRequest() {
        return new TaskRequest(
                "",
                false
        );
    }


}
