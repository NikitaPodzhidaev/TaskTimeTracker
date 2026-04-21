package ru.testcdek.tasktimetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testcdek.tasktimetracker.dto.request.CreateTimeRecordRequest;
import ru.testcdek.tasktimetracker.dto.response.TimeRecordResponse;
import ru.testcdek.tasktimetracker.mapper.TaskMapper;
import ru.testcdek.tasktimetracker.mapper.TimeRecordMapper;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TimeRecord;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TimeRecordServiceImpl implements TimeRecordService{

    private final TimeRecordMapper timeRecordMapper;
    private final TaskMapper taskMapper;

    @Override
    public TimeRecordResponse createTimeRecord(CreateTimeRecordRequest request) {
        Task task = taskMapper.findTaskById(request.taskId());
        Objects.requireNonNull(task, "Task with this id wasn't found");

        TimeRecord timeRecord = TimeRecord.builder()
                .employeeId(request.employeeId())
                .taskId(request.taskId())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .workDescription(request.workDescription())
                .build();
        timeRecordMapper.insertTimeRecord(timeRecord);
        return mapToTimeRecordResponse(timeRecord);
    }

    @Override
    public List<TimeRecordResponse> getEmployeeTimeRecords(Long employeeId, LocalDateTime startTime, LocalDateTime endTime) {
        return List.of();
    }

    private TimeRecordResponse mapToTimeRecordResponse(TimeRecord timeRecord){
        return new TimeRecordResponse(
                timeRecord.getId(),
                timeRecord.getEmployeeId(),
                timeRecord.getTaskId(),
                timeRecord.getStartTime(),
                timeRecord.getEndTime(),
                timeRecord.getWorkDescription()
        );
    }
}
