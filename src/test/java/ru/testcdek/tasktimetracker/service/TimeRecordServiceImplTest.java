package ru.testcdek.tasktimetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.testcdek.tasktimetracker.dto.request.CreateTimeRecordRequest;
import ru.testcdek.tasktimetracker.dto.response.TimeRecordResponse;
import ru.testcdek.tasktimetracker.exception.InvalidTimeRecordException;
import ru.testcdek.tasktimetracker.exception.TaskNotFoundException;
import ru.testcdek.tasktimetracker.mapper.TaskMapper;
import ru.testcdek.tasktimetracker.mapper.TimeRecordMapper;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;
import ru.testcdek.tasktimetracker.model.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceImplTest {

    @Mock
    private TimeRecordMapper timeRecordMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TimeRecordServiceImpl timeRecordService;

    @Test
    void createTimeRecord_shouldSaveWhenTaskExists() {
        Task task = Task.builder()
                .id(1L)
                .title("Task")
                .description("Desc")
                .status(TaskStatus.NEW)
                .build();

        CreateTimeRecordRequest request = new CreateTimeRecordRequest(
                101L,
                1L,
                LocalDateTime.of(2026, 4, 21, 10, 0),
                LocalDateTime.of(2026, 4, 21, 12, 0),
                "Implemented service"
        );

        when(taskMapper.findTaskById(1L)).thenReturn(task);

        doAnswer(invocation -> {
            TimeRecord timeRecord = invocation.getArgument(0);
            timeRecord.setId(1L);
            return null;
        }).when(timeRecordMapper).insertTimeRecord(any(TimeRecord.class));

        TimeRecordResponse response = timeRecordService.createTimeRecord(request);

        ArgumentCaptor<TimeRecord> captor = ArgumentCaptor.forClass(TimeRecord.class);
        verify(timeRecordMapper).insertTimeRecord(captor.capture());

        TimeRecord saved = captor.getValue();
        assertEquals(101L, saved.getEmployeeId());
        assertEquals(1L, saved.getTaskId());
        assertEquals("Implemented service", saved.getWorkDescription());

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(101L, response.employeeId());
        assertEquals(1L, response.taskId());
    }

    @Test
    void createTimeRecord_shouldThrowWhenTaskNotFound() {
        CreateTimeRecordRequest request = new CreateTimeRecordRequest(
                101L,
                999L,
                LocalDateTime.of(2026, 4, 21, 10, 0),
                LocalDateTime.of(2026, 4, 21, 12, 0),
                "Implemented service"
        );

        when(taskMapper.findTaskById(999L)).thenReturn(null);

        assertThrows(TaskNotFoundException.class,
                () -> timeRecordService.createTimeRecord(request));

        verify(timeRecordMapper, never()).insertTimeRecord(any());
    }

    @Test
    void createTimeRecord_shouldThrowWhenEndTimeBeforeStartTime() {
        Task task = Task.builder()
                .id(1L)
                .title("Task")
                .description("Desc")
                .status(TaskStatus.NEW)
                .build();

        CreateTimeRecordRequest request = new CreateTimeRecordRequest(
                101L,
                1L,
                LocalDateTime.of(2026, 4, 21, 12, 0),
                LocalDateTime.of(2026, 4, 21, 10, 0),
                "Wrong time range"
        );

        when(taskMapper.findTaskById(1L)).thenReturn(task);

        assertThrows(InvalidTimeRecordException.class,
                () -> timeRecordService.createTimeRecord(request));

        verify(timeRecordMapper, never()).insertTimeRecord(any());
    }

    @Test
    void getEmployeeTimeRecords_shouldReturnList() {
        TimeRecord record = TimeRecord.builder()
                .id(1L)
                .employeeId(101L)
                .taskId(1L)
                .startTime(LocalDateTime.of(2026, 4, 21, 10, 0))
                .endTime(LocalDateTime.of(2026, 4, 21, 12, 0))
                .workDescription("Implemented controller")
                .build();

        when(timeRecordMapper.findTimeRecordsByEmployeeIdAndPeriod(
                eq(101L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(List.of(record));

        List<TimeRecordResponse> result = timeRecordService.getEmployeeTimeRecords(
                101L,
                LocalDateTime.of(2026, 4, 21, 0, 0),
                LocalDateTime.of(2026, 4, 21, 23, 59)
        );

        assertEquals(1, result.size());
        assertEquals(101L, result.getFirst().employeeId());
        assertEquals(1L, result.getFirst().taskId());
    }
}