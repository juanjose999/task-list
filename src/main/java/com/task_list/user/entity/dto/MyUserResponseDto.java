package com.task_list.user.entity.dto;

import lombok.Builder;

@Builder
public record MyUserResponseDto(String fullName, String email) {
}
