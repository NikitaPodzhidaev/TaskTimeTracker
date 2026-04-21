package ru.testcdek.tasktimetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testcdek.tasktimetracker.dto.request.CreateTaskRequest;
import ru.testcdek.tasktimetracker.dto.request.UpdateTaskStatusRequest;
import ru.testcdek.tasktimetracker.dto.response.TaskResponse;
import ru.testcdek.tasktimetracker.mapper.TaskMapper;
import ru.testcdek.tasktimetracker.model.Task;
import ru.testcdek.tasktimetracker.model.TaskStatus;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .taskStatus(TaskStatus.NEW)
                .build();
        taskMapper.insertTask(task);
        return mapToTaskResponse(task);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskMapper.findTaskById(id);
        Objects.requireNonNull(task, "Task wasn't found with this id");
        return mapToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request) {
        Task existingTask = taskMapper.findTaskById(id);
        Objects.requireNonNull(existingTask, "Task wasn't found with this id");
        taskMapper.updateTaskStatus(id, request.taskStatus());

        Task updatedTask = taskMapper.findTaskById(id);
        return mapToTaskResponse(updatedTask);
    }

    private TaskResponse mapToTaskResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus()
        );
    }
}
