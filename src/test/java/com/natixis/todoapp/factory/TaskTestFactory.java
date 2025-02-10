package com.natixis.todoapp.factory;

import com.natixis.todoapp.domain.model.Task;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TaskTestFactory {
    public static Task createTask() {
        return new Task(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                "Finir l'application back",
                false
        );
    }

    public static Task createTaskWithId(UUID id) {
        return new Task(
                id,
                "Finir l'application back",
                false
        );
    }

    public static Task createTaskCompleted() {
        return new Task(
                UUID.fromString("4fa85f64-5717-4562-b3fc-2c963f66afa6"),
                "Lancer l'application back",
                true
        );
    }

    public static Task createTaskUncompleted() {
        return new Task(
                UUID.fromString("5fa85f64-5717-4562-b3fc-2c963f66afa6"),
                "Partir en vacances",
                false
        );
    }

    public static Task createTaskWithEmptyLabel() {
        return new Task(
                "",
                false
        );
    }

    public static Task createTaskWithNullLabel() {
        return new Task(
                null,
                false
        );
    }


    public static List<Task> createCompletedTasks() {
        return Arrays.asList(
                new Task("Task 1", true),
                new Task("Task 2", true)
        );
    }

    public static List<Task> createIncompleteTasks() {
        return Arrays.asList(
                new Task("Task 1", false),
                new Task("Task 2", false)
        );
    }

    public static List<Task> createMixedTasks() {
        return Arrays.asList(
                new Task("Task 1", false),
                new Task("Task 2", true)
        );
    }

    public static List<Task> createAllTasks() {
        return Arrays.asList(
                new Task("Task 1", false),
                new Task("Task 2", true),
                new Task("Task 3", false)
        );
    }
}
