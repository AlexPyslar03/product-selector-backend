package com.alexpyslar03.productselectorbackend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * DTO для передачи данных при создании нового пользователя.
 * <p>
 * Этот класс включает в себя данные, необходимые для создания нового пользователя
 * и включает валидационные аннотации для проверки значений полей
 * и аннотации Swagger для документации API.
 * </p>
 * <ul>
 *     <li>username — Имя пользователя (должно быть уникальным)</li>
 *     <li>email — Адрес электронной почты (должен быть уникальным и в корректном формате)</li>
 *     <li>password — Пароль пользователя (должен быть зашифрован)</li>
 *     <li>birthDate — Дата рождения пользователя</li>
 * </ul>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание пользователя")
public class UserCreateRequest implements UserDetails {

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
     * Пароль пользователя.
     * Должен быть зашифрован и не может быть пустым.
     */
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    /**
     * Дата рождения пользователя.
     * Поле обязательно для заполнения.
     */
    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    @NotNull(message = "Дата рождения не может быть пустой")
    private LocalDate birthDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}