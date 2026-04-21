package com.ufinet.autos.auth.domain.ports.input;

public interface AuthUseCase {
    TokenResult register(String name, String email, String password);
    TokenResult login(String email, String password);

    record TokenResult(String token, String name, String email) {}
}
