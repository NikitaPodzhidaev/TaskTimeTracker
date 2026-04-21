package ru.testcdek.tasktimetracker.dto.response;


import ru.testcdek.tasktimetracker.model.TaskStatus;

public record TaskResponse(Long id, String title,
                           String description,
                           TaskStatus taskStatus) {

}
