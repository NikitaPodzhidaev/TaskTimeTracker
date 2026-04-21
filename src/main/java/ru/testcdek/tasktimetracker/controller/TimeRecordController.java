package ru.testcdek.tasktimetracker.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.testcdek.tasktimetracker.dto.request.CreateTimeRecordRequest;
import ru.testcdek.tasktimetracker.dto.response.TimeRecordResponse;
import ru.testcdek.tasktimetracker.service.TimeRecordService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-records")
@RequiredArgsConstructor
public class TimeRecordController {

    private final TimeRecordService timeRecordService;

    @PostMapping
    public TimeRecordResponse createTimeRecord(@Valid @RequestBody CreateTimeRecordRequest request) {
        return timeRecordService.createTimeRecord(request);
    }

    @GetMapping
    public List<TimeRecordResponse> getEmployeeTimeRecords(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        return timeRecordService.getEmployeeTimeRecords(employeeId, startTime, endTime);
    }


}
