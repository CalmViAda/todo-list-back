package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public enum TaskFilterStrategy {
    ALL {
        @Override
        public List<Task> filter(List<Task> tasks) {
            return tasks;
        }
    },
    STATUS {
        @Override
        public List<Task> filter(List<Task> tasks) {
            return tasks.stream()
                    .filter(task -> !task.isComplete())
                    .collect(Collectors.toList());
        }
    };

    public abstract List<Task> filter(List<Task> tasks);

    public static TaskFilterStrategy fromString(String filter) {
        for (TaskFilterStrategy strategy : values()) {
            if (strategy.name().equalsIgnoreCase(filter)) {
                return strategy;
            }
        }
        return null;
    }
}