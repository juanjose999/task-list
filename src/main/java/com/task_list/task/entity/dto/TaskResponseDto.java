package com.task_list.task.entity.dto;

import lombok.Builder;

@Builder
public record TaskResponseDto(String title, String description, String status, String priority,String dateCreated) {
}
