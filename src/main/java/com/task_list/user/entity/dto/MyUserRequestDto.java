package com.task_list.user.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder

public record MyUserRequestDto(
        @NotBlank(message = "El Nombre no puede estar vacío")String fullName,
        @NotBlank(message = "El email no puede estar vacío")String email,
        @NotBlank(message = "La contraseña no puede estar vacío")String password) {
}
