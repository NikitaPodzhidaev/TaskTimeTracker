package ru.testcdek.tasktimetracker.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.testcdek.tasktimetracker.dto.request.CreateTaskRequest;
import ru.testcdek.tasktimetracker.dto.request.UpdateTaskStatusRequest;
import ru.testcdek.tasktimetracker.dto.response.TaskResponse;
import ru.testcdek.tasktimetracker.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(@PathVariable Long id,
                                         @Valid @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateTaskStatus(id, request);
    }

}
