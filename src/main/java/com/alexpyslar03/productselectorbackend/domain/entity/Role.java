package com.alexpyslar03.productselectorbackend.domain.entity;

/**
 * Перечисление уровней доступа пользователей.
 */
public enum Role {
    ROLE_USER,        // Обычный пользователь
    ROLE_ADMIN,       // Администратор
    ROLE_SUPER_ADMIN  // Супер администратор
}
