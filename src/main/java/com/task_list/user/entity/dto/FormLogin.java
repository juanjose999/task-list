package com.task_list.user.entity.dto;

import jakarta.validation.constraints.NotBlank;

public record FormLogin(@NotBlank(message = "El email no puede estar vacío")String email,
                        @NotBlank(message = "La contraseña no puede estar vacía")String password) {
}
