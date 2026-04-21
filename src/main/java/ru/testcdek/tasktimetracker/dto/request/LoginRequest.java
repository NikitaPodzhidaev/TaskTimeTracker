package ru.testcdek.tasktimetracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Username mustn't be blank")
        String username,

        @NotBlank(message = "Password mustn't be blank")
        String password

) {
}
