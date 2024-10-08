package com.alexpyslar03.productselectorbackend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи данных при аутентификации пользователя.
 * <p>
 * Этот класс используется для сбора данных, необходимых для аутентификации
 * пользователя в системе. Включает валидационные аннотации для проверки
 * значений полей и аннотации Swagger для документации API.
 * </p>
 * <ul>
 *     <li>username — Имя пользователя (должно быть уникальным)</li>
 *     <li>password — Пароль пользователя (должен быть зашифрован)</li>
 * </ul>
 */
@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    /**
     * Имя пользователя.
     * Должно содержать от 5 до 50 символов и не может быть пустым.
     */
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    /**
     * Пароль пользователя.
     * Должен содержать от 8 до 255 символов и не может быть пустым.
     */
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
