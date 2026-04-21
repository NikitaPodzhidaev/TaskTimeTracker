package ru.testcdek.tasktimetracker.service;

import ru.testcdek.tasktimetracker.dto.request.CreateTimeRecordRequest;
import ru.testcdek.tasktimetracker.dto.response.TimeRecordResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordService {

    TimeRecordResponse createTimeRecord(CreateTimeRecordRequest request);

    List<TimeRecordResponse> getEmployeeTimeRecords(Long employeeId,
                                                    LocalDateTime startTime,
                                                    LocalDateTime endTime);

}
