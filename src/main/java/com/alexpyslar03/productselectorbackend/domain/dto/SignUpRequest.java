package com.alexpyslar03.productselectorbackend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO для передачи данных при регистрации пользователя.
 * <p>
 * Этот класс используется для сбора данных, необходимых для регистрации
 * нового пользователя в системе. Включает валидационные аннотации для
 * проверки значений полей и аннотации Swagger для документации API.
 * </p>
 * <ul>
 *     <li>username — Имя пользователя (должно быть уникальным)</li>
 *     <li>email — Адрес электронной почты (должен быть уникальным и в корректном формате)</li>
 *     <li>birthDate — Дата рождения пользователя</li>
 *     <li>password — Пароль пользователя (должен быть зашифрован)</li>
 * </ul>
 */
@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    /**
     * Имя пользователя.
     * Должно содержать от 5 до 50 символов и не может быть пустым.
     */
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    /**
     * Адрес электронной почты пользователя.
     * Должен быть уникальным, не может быть пустым, и должен быть в формате user@example.com.
     */
    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    /**
     * Дата рождения пользователя.
     * Поле может быть пустым, но рекомендуется указывать.
     */
    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    private LocalDate birthDate;

    /**
     * Пароль пользователя.
     * Должен содержать не более 255 символов.
     */
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}