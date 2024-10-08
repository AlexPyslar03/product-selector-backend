package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.JwtAuthenticationResponse;
import com.alexpyslar03.productselectorbackend.domain.dto.SignInRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.SignUpRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.UserCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Async
    public CompletableFuture<JwtAuthenticationResponse> signUp(SignUpRequest request) {
        var user = UserCreateRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return CompletableFuture.completedFuture(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Async
    public CompletableFuture<JwtAuthenticationResponse> signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return CompletableFuture.completedFuture(new JwtAuthenticationResponse(jwt));
    }
}