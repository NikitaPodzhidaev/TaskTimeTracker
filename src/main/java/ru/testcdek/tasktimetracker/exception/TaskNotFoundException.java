package ru.testcdek.tasktimetracker.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id){
        super("Task with id " + id + " wasn't found");
    }

}
