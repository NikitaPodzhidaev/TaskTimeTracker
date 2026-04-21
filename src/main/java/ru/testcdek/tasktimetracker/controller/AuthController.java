package ru.testcdek.tasktimetracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.testcdek.tasktimetracker.dto.request.LoginRequest;
import ru.testcdek.tasktimetracker.dto.response.AuthResponse;
import ru.testcdek.tasktimetracker.security.CustomUserDetailsService;
import ru.testcdek.tasktimetracker.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}