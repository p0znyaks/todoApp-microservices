package com.p0znyaks.todo_app.exception;

public class AccessForbiddenException extends RuntimeException {

    public AccessForbiddenException(Long entityId, String entityName) {
        super("Access to " + entityName + " with id = " + entityId + " is forbidden");
    }
}
