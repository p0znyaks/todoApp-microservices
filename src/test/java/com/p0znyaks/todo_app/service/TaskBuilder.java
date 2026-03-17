package com.p0znyaks.todo_app.service;

import com.p0znyaks.todo_app.dto.TaskRequest;
import com.p0znyaks.todo_app.entity.Task;

public class TaskBuilder {

    public static Task.TaskBuilder task() {
        return Task.builder()
                .id(1L)
                .title("New Task")
                .description("New Desc")
                .completed(true);
    }

    public static TaskRequest.TaskRequestBuilder taskRequest() {
        return TaskRequest.builder()
                .title("New Task")
                .description("New Desc")
                .completed(true);
    }
}
