package com.alexpyslar03.productselectorbackend.domain.dto;

import com.alexpyslar03.productselectorbackend.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    /**
     * Уникальный идентификатор пользователя.
     * ID генерируется автоматически с использованием последовательности.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Адрес электронной почты пользователя.
     * Должен быть уникальным и не может быть null.
     */
    private String email;

    /**
     * Пароль пользователя.
     * Не должен быть null. Хранится в зашифрованном виде.
     */
    private String password;

    /**
     * Дата рождения пользователя.
     * Не может быть null.
     */
    private LocalDate birthDate;

    /**
     * Уровень доступа пользователя.
     * Использует перечисление AccessLevel из сущности User.
     */
    private Role role;
}
