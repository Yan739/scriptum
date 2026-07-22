package com.yann.scriptum.service;

import com.yann.scriptum.dto.AuthResponse;
import com.yann.scriptum.dto.LoginRequest;
import com.yann.scriptum.dto.RegisterRequest;
import com.yann.scriptum.model.User;
import com.yann.scriptum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /** Cree un nouvel utilisateur avec mot de passe encode et retourne un token JWT. */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Un compte existe deja avec cet email");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail());
    }

    /** Authentifie l'utilisateur et retourne un token JWT si les identifiants sont valides. */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("Utilisateur introuvable"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail());
    }
}