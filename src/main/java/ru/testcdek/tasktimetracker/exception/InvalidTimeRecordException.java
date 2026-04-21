package ru.testcdek.tasktimetracker.exception;

public class InvalidTimeRecordException extends RuntimeException {
    public InvalidTimeRecordException(String message) {
        super(message);
    }
}
