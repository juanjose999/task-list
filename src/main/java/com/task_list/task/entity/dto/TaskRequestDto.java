package com.task_list.task.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TaskRequestDto(@NotBlank(message = "El título no puede estar vacío") String title,
                             @NotBlank(message = "La descripcion no puede estar vacía") String description, String priority, String status) {
}
