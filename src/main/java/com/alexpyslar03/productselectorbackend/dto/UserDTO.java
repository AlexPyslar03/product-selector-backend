package com.alexpyslar03.productselectorbackend.dto;

import com.alexpyslar03.productselectorbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для передачи данных о пользователе.
 * Используется для обмена данными между слоями приложения и внешними системами.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Фамилия пользователя.
     */
    private String surname;

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
     * Дата регистрации пользователя.
     * Не может быть null.
     */
    private LocalDate registrationDate;

    /**
     * Уровень доступа пользователя.
     * Использует перечисление AccessLevel из сущности User.
     */
    private User.AccessLevel accessLevel;
}