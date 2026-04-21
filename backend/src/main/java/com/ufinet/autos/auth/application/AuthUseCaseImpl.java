package com.ufinet.autos.auth.application;

import com.ufinet.autos.auth.domain.model.User;
import com.ufinet.autos.auth.domain.ports.input.AuthUseCase;
import com.ufinet.autos.auth.domain.ports.output.UserRepository;
import com.ufinet.autos.shared.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResult register(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.create(name, email, passwordEncoder.encode(password));
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getEmail());
        return new TokenResult(token, saved.getName(), saved.getEmail());
    }

    @Override
    public TokenResult login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        return new TokenResult(token, user.getName(), user.getEmail());
    }
}
