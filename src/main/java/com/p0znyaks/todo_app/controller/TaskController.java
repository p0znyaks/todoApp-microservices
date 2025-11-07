package com.p0znyaks.todo_app.controller;

import com.p0znyaks.todo_app.dto.TaskRequest;
import com.p0znyaks.todo_app.dto.TaskResponse;
import com.p0znyaks.todo_app.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasksByUser(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskByUser(@PathVariable Long userId, @PathVariable Long taskId) {
        return taskService.getTaskByUserId(userId, taskId);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long userId, @Valid @RequestBody TaskRequest request) {
        TaskResponse createdTask = taskService.createTask(userId, request);
        return ResponseEntity
                .created(URI.create("/api/users" + userId + "/tasks" ))
                .body(createdTask);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(@PathVariable Long userId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        return taskService.updateTask(userId, taskId, request);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(userId, taskId);
    }
}