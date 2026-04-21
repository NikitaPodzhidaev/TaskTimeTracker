package ru.testcdek.tasktimetracker.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;
import ru.testcdek.tasktimetracker.support.AbstractPostgresContainerTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperIntegrationTest extends AbstractPostgresContainerTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void insertTask_andFindById_shouldWork() {
        Task task = Task.builder()
                .title("Integration test task")
                .description("Task mapper test")
                .status(TaskStatus.NEW)
                .build();

        taskMapper.insertTask(task);

        assertNotNull(task.getId());

        Task foundTask = taskMapper.findTaskById(task.getId());

        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        assertEquals("Integration test task", foundTask.getTitle());
        assertEquals("Task mapper test", foundTask.getDescription());
        assertEquals(TaskStatus.NEW, foundTask.getStatus());
    }

    @Test
    void updateTaskStatus_shouldWork() {
        Task task = Task.builder()
                .title("Task for update")
                .description("Before update")
                .status(TaskStatus.NEW)
                .build();

        taskMapper.insertTask(task);

        taskMapper.updateTaskStatus(task.getId(), TaskStatus.DONE);

        Task updatedTask = taskMapper.findTaskById(task.getId());

        assertNotNull(updatedTask);
        assertEquals(TaskStatus.DONE, updatedTask.getStatus());
    }
}