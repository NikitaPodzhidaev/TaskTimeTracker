package ru.testcdek.tasktimetracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.testcdek.tasktimetracker.dto.request.CreateTaskRequest;
import ru.testcdek.tasktimetracker.dto.request.UpdateTaskStatusRequest;
import ru.testcdek.tasktimetracker.dto.response.TaskResponse;
import ru.testcdek.tasktimetracker.exception.TaskNotFoundException;
import ru.testcdek.tasktimetracker.mapper.TaskMapper;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;
import ru.testcdek.tasktimetracker.service.TaskServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_shouldSetStatusNew() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Test task",
                "Test description"
        );

        doAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return null;
        }).when(taskMapper).insertTask(any(Task.class));

        TaskResponse response = taskService.createTask(request);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskMapper).insertTask(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();

        assertEquals("Test task", savedTask.getTitle());
        assertEquals("Test description", savedTask.getDescription());
        assertEquals(TaskStatus.NEW, savedTask.getStatus());

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Test task", response.title());
        assertEquals("Test description", response.description());
        assertEquals(TaskStatus.NEW, response.taskStatus());
    }

    @Test
    void getTaskById_shouldReturnTaskResponse() {
        Task task = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description")
                .status(TaskStatus.NEW)
                .build();

        when(taskMapper.findTaskById(1L)).thenReturn(task);

        TaskResponse response = taskService.getTaskById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Task 1", response.title());
        assertEquals("Description", response.description());
        assertEquals(TaskStatus.NEW, response.taskStatus());

        verify(taskMapper).findTaskById(1L);
    }

    @Test
    void getTaskById_shouldThrowWhenTaskNotFound() {
        when(taskMapper.findTaskById(999L)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(999L));

        verify(taskMapper).findTaskById(999L);
    }

    @Test
    void updateTaskStatus_shouldUpdateAndReturnUpdatedTask() {
        Task existingTask = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description")
                .status(TaskStatus.NEW)
                .build();

        Task updatedTask = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description")
                .status(TaskStatus.IN_PROGRESS)
                .build();

        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest(TaskStatus.IN_PROGRESS);

        when(taskMapper.findTaskById(1L))
                .thenReturn(existingTask)
                .thenReturn(updatedTask);

        doNothing().when(taskMapper).updateTaskStatus(1L, TaskStatus.IN_PROGRESS);

        TaskResponse response = taskService.updateTaskStatus(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(TaskStatus.IN_PROGRESS, response.taskStatus());

        verify(taskMapper, times(2)).findTaskById(1L);
        verify(taskMapper).updateTaskStatus(1L, TaskStatus.IN_PROGRESS);
    }

    @Test
    void updateTaskStatus_shouldThrowWhenTaskNotFound() {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest(TaskStatus.DONE);

        when(taskMapper.findTaskById(1L)).thenReturn(null);

        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTaskStatus(1L, request));

        verify(taskMapper).findTaskById(1L);
        verify(taskMapper, never()).updateTaskStatus(anyLong(), any());
    }
}