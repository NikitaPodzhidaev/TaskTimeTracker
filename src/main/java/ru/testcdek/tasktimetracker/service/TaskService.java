package ru.testcdek.tasktimetracker.service;

import ru.testcdek.tasktimetracker.dto.request.CreateTaskRequest;
import ru.testcdek.tasktimetracker.dto.request.UpdateTaskStatusRequest;
import ru.testcdek.tasktimetracker.dto.response.TaskResponse;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse getTaskById(Long id);
    TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request);


}
