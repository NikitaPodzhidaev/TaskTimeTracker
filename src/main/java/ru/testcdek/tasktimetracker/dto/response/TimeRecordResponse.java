package ru.testcdek.tasktimetracker.dto.response;

import java.time.LocalDateTime;

public record TimeRecordResponse(
        Long id,
        Long employeeId,
        Long taskId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String workDescription
) {
}
