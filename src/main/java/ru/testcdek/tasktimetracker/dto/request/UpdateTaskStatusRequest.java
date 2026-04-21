package ru.testcdek.tasktimetracker.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.testcdek.tasktimetracker.model.TaskStatus;

public record UpdateTaskStatusRequest(
        @NotNull(message = "Status mustn't be null")
        TaskStatus taskStatus
) {
}
