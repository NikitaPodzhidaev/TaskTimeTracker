package ru.testcdek.tasktimetracker.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeRecord {
    private Long id;
    private Long employeeId;
    private Long taskId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String workDescription;

}
