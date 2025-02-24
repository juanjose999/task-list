package com.task_list.task.entity.dto;

import com.task_list.task.entity.Task;

public class TaskMapper {

    public static Task taskRequestToEntity(TaskRequestDto taskRequestDto) {
        return Task.builder()
                .title(taskRequestDto.title())
                .description(taskRequestDto.description())
                .priority(Task.Priority.valueOf(taskRequestDto.priority()))
                .status(Task.Status.valueOf(taskRequestDto.status()))
                .build();
    }

    public static TaskResponseDto entityToTaskResponseDto(final Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(String.valueOf(task.getStatus()).toLowerCase())
                .priority(String.valueOf(task.getPriority()).toLowerCase())
                .dateCreated(task.getDateCreated())
                .build();
    }

}
