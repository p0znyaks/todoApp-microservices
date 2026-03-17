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
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse createdTask = taskService.createTask(request);
        return ResponseEntity
                .created(URI.create("/tasks"))
                .body(createdTask);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(@PathVariable Long userId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        return taskService.updateTask(userId, taskId, request);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}