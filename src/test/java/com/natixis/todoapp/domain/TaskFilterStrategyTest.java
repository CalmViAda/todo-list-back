package com.natixis.todoapp.domain;

import com.natixis.todoapp.domain.model.Task;
import com.natixis.todoapp.factory.TaskTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFilterStrategyTest {

    @Test
    void filter_all_should_return_all_tasks() {
        List<Task> tasks = TaskTestFactory.createAllTasks();

        List<Task> result = TaskFilterStrategy.ALL.filter(tasks);

        assertEquals(3, result.size());
    }

    @Test
    void filter_status_should_return_incomplete_tasks() {
        List<Task> tasks = TaskTestFactory.createAllTasks();

        List<Task> result = TaskFilterStrategy.STATUS.filter(tasks);

        assertEquals(2, result.size());
    }

    @Test
    void from_string_should_return_all_when_filter_is_all() {
        TaskFilterStrategy result = TaskFilterStrategy.fromString("ALL");

        assertNotNull(result);
        assertEquals(TaskFilterStrategy.ALL, result);
    }

    @Test
    void from_string_should_return_status_when_filter_is_status() {
        TaskFilterStrategy result = TaskFilterStrategy.fromString("STATUS");

        assertNotNull(result);
        assertEquals(TaskFilterStrategy.STATUS, result);
    }

    @Test
    void from_string_should_return_null_when_filter_is_invalid() {
        TaskFilterStrategy result = TaskFilterStrategy.fromString("INVALID");

        assertNull(result);
    }

}