package com.alexpyslar03.productselectorbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Сущность, представляющая пользователя в базе данных.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Уникальный идентификатор пользователя.
     * ID генерируется автоматически с использованием последовательности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Имя пользователя.
     * Не может быть null.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Фамилия пользователя.
     * Не может быть null.
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * Адрес электронной почты пользователя.
     * Должен быть уникальным и не может быть null.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Пароль пользователя.
     * Не может быть null.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Дата рождения пользователя.
     * Не может быть null. По умолчанию устанавливается текущая дата.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate = LocalDate.now();

    /**
     * Дата регистрации пользователя.
     * Не может быть null. По умолчанию устанавливается текущая дата.
     */
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    /**
     * Уровень доступа пользователя.
     * Хранится как строковое значение в базе данных.
     * По умолчанию установлен уровень доступа USER.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AccessLevel accessLevel = AccessLevel.USER;

    /**
     * Перечисление уровней доступа пользователей.
     */
    public enum AccessLevel {
        USER,        // Обычный пользователь
        ADMIN,       // Администратор
        SUPER_ADMIN  // Супер администратор
    }
}