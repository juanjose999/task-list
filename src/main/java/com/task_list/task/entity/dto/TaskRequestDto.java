package com.task_list.task.entity.dto;

import lombok.Builder;

@Builder
public record TaskRequestDto(String title, String description, String priority) {
}
