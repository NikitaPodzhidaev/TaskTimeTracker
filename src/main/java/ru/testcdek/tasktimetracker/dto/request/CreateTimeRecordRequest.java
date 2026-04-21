package ru.testcdek.tasktimetracker.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTimeRecordRequest(
        @NotNull(message = "Employee id mustn't be null")
        Long employeeId,

        @NotNull(message = "Task id mustn't be null")
        Long taskId,

        @NotNull(message = "Start time mustn't be null")
        LocalDateTime startTime,

        @NotNull(message = "End time mustn't null")
        LocalDateTime endTime,

        @Size(max = 1000, message = "Work description mustn't be > 1000 characters")
        String workDescription
) {
}