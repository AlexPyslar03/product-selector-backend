package com.alexpyslar03.productselectorbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Сущность, представляющая пользователя в базе данных.
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
    @Column(name = "username",  nullable = false, unique = true)
    private String username;

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
    @Column(name = "role", nullable = false)
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