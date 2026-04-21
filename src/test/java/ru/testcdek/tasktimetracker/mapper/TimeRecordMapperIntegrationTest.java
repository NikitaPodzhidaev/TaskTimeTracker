package ru.testcdek.tasktimetracker.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;
import ru.testcdek.tasktimetracker.model.TimeRecord;
import ru.testcdek.tasktimetracker.support.AbstractPostgresContainerTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class TimeRecordMapperIntegrationTest extends AbstractPostgresContainerTest {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TimeRecordMapper timeRecordMapper;

    @Test
    void insertTimeRecord_andFindByEmployeeIdAndPeriod_shouldWork() {
        Task task = Task.builder()
                .title("Task for time record")
                .description("TimeRecord mapper test")
                .status(TaskStatus.NEW)
                .build();

        taskMapper.insertTask(task);

        TimeRecord timeRecord = TimeRecord.builder()
                .employeeId(101L)
                .taskId(task.getId())
                .startTime(LocalDateTime.of(2026, 4, 21, 10, 0))
                .endTime(LocalDateTime.of(2026, 4, 21, 12, 0))
                .workDescription("Worked on integration test")
                .build();

        timeRecordMapper.insertTimeRecord(timeRecord);

        assertNotNull(timeRecord.getId());

        List<TimeRecord> records = timeRecordMapper.findTimeRecordsByEmployeeIdAndPeriod(
                101L,
                LocalDateTime.of(2026, 4, 21, 0, 0),
                LocalDateTime.of(2026, 4, 21, 23, 59)
        );

        assertEquals(1, records.size());

        TimeRecord found = records.get(0);
        assertEquals(101L, found.getEmployeeId());
        assertEquals(task.getId(), found.getTaskId());
        assertEquals("Worked on integration test", found.getWorkDescription());
    }
}