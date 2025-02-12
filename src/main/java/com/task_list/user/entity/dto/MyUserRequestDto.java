package com.task_list.user.entity.dto;

import lombok.Builder;

@Builder

public record MyUserRequestDto(String fullName, String email, String password) {
}
