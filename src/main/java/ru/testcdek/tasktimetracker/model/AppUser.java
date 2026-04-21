package ru.testcdek.tasktimetracker.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class AppUser {
    private Long id;
    private String username;
    private String password;
    private String role;
}
