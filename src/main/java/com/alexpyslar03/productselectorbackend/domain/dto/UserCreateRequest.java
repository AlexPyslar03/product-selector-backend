package com.alexpyslar03.productselectorbackend.domain.dto;

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
 * DTO (Data Transfer Object) для передачи данных о пользователе.
 * Используется для обмена данными между слоями приложения и внешними системами.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}