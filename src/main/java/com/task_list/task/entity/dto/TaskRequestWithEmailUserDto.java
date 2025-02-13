package com.task_list.task.entity.dto;

import lombok.Builder;

@Builder
public record TaskRequestWithEmailUserDto(String emailUser, String title, String description) {
}
