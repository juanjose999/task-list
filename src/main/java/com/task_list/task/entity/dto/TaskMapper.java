package com.task_list.task.entity.dto;

import com.task_list.task.entity.Task;

public class TaskMapper {

    public static Task taskRequestToEntity(final TaskRequestDto taskRequestDto) {
        return Task.builder()
                .title(taskRequestDto.title())
                .description(taskRequestDto.description())
                .priority(Task.Priority.valueOf(taskRequestDto.priority()))
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

    public static Task taskRequestSaveToEntity(final TaskRequestWithEmailUserDto taskRequestWithEmailUserDto) {
        return Task.builder()
                .title(taskRequestWithEmailUserDto.title())
                .description(taskRequestWithEmailUserDto.description())
                .build();
    }
}
