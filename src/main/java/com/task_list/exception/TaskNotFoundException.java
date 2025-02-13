package com.task_list.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
