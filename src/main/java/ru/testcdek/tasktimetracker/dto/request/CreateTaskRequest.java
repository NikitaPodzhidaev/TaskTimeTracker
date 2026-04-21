package ru.testcdek.tasktimetracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "Title mustn't be empty")
        @Size(max = 255, message = "Title mustn't be > 255 characters")
        String title,

        @Size(max = 1000, message = "Description mustn't be > 1000 characters")
        String description
) {
}
