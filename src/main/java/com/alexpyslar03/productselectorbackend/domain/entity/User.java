package com.alexpyslar03.productselectorbackend.domain.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Модель пользователя.
 * <p>
 * Этот класс представляет сущность пользователя в системе и включает в себя
 * поля, необходимые для хранения информации о пользователе и его авторизации.
 * </p>
 * <ul>
 *     <li>id — Уникальный идентификатор пользователя</li>
 *     <li>username — Имя пользователя (не может быть пустым и уникальным)</li>
 *     <li>email — Электронная почта пользователя (не может быть пустой и уникальной)</li>
 *     <li>password — Пароль пользователя (не может быть пустым)</li>
 *     <li>birthDate — Дата рождения пользователя</li>
 *     <li>registrationDate — Дата регистрации пользователя</li>
 *     <li>role — Роль пользователя в системе</li>
 * </ul>
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     * Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    /**
     * Имя пользователя.
     * Не должно быть пустым и должно быть уникальным.
     */
    @Column(name = "username", nullable = false, unique = true)
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    /**
     * Электронная почта пользователя.
     * Не должна быть пустой и должна быть уникальной.
     */
    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Электронная почта пользователя", example = "user@example.com")
    private String email;

    /**
     * Пароль пользователя.
     * Не должен быть пустым.
     */
    @Column(name = "password", nullable = false)
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;

    /**
     * Дата рождения пользователя.
     * Необязательное поле.
     */
    @Column(name = "birth_date", nullable = false)
    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    private LocalDate birthDate = LocalDate.now();

    /**
     * Дата регистрации пользователя.
     * Необязательное поле.
     */
    @Column(name = "registration_date", nullable = false)
    @Schema(description = "Дата регистрации пользователя", example = "2024-10-08")
    private LocalDate registrationDate = LocalDate.now();

    /**
     * Роль пользователя в системе.
     * Обязательное поле.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}