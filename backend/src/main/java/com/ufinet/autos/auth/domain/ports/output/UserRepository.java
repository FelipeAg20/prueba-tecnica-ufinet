package com.ufinet.autos.auth.domain.ports.output;

import com.ufinet.autos.auth.domain.model.User;

import java.util.Optional;

// Puerto de salida: el dominio define qué necesita, sin saber cómo se implementa
public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
