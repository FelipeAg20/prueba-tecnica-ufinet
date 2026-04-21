package com.ufinet.autos.auth.infrastructure.rest;

import com.ufinet.autos.auth.infrastructure.rest.dto.AuthResponse;
import com.ufinet.autos.auth.infrastructure.rest.dto.LoginRequest;
import com.ufinet.autos.auth.infrastructure.rest.dto.RegisterRequest;
import com.ufinet.autos.auth.domain.ports.input.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        var result = authUseCase.register(request.name(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(result.token(), result.name(), result.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        var result = authUseCase.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(result.token(), result.name(), result.email()));
    }
}
