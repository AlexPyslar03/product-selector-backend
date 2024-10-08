package com.alexpyslar03.productselectorbackend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи ответа с токеном доступа.
 * <p>
 * Этот класс используется для возвращения токена аутентификации пользователю
 * после успешного входа в систему. Он содержит токен, который может быть
 * использован для последующих запросов к защищённым ресурсам API.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с токеном доступа")
public class JwtAuthenticationResponse {

    /**
     * Токен доступа.
     * Этот токен используется для аутентификации пользователя
     * при обращении к защищённым ресурсам.
     */
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private String token;
}