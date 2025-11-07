package com.p0znyaks.todo_app.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException() {
        super("Task not found");
    }
}
