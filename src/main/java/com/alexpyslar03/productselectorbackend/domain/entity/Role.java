package com.alexpyslar03.productselectorbackend.domain.entity;

/**
 * Перечисление уровней доступа пользователей.
 */
public enum Role {
    ROLE_USER,       // Обычный пользователь
    ROLE_MODERATOR,  // Модератор
    ROLE_ADMIN       // Администратор
}
