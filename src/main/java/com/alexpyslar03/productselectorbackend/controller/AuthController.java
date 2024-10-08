package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.domain.dto.JwtAuthenticationResponse;
import com.alexpyslar03.productselectorbackend.domain.dto.SignInRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.SignUpRequest;
import com.alexpyslar03.productselectorbackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * Контроллер для аутентификации пользователей.
 * Предоставляет методы для регистрации и авторизации.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    /**
     * Регистрация пользователя
     *
     * @param request данные для регистрации
     * @return ответ с токеном
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public CompletableFuture<ResponseEntity<JwtAuthenticationResponse>> signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request)
                .thenApply(ResponseEntity::ok);
    }

    /**
     * Авторизация пользователя
     *
     * @param request данные для авторизации
     * @return ответ с токеном
     */
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public CompletableFuture<ResponseEntity<JwtAuthenticationResponse>> signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request)
                .thenApply(ResponseEntity::ok);
    }
}