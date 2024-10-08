package com.alexpyslar03.productselectorbackend.domain.entity;

/**
 * Перечисление ролей пользователей.
 * <p>
 * Этот класс представляет различные роли, которые могут быть присвоены
 * пользователям в системе. Каждая роль определяет уровень доступа
 * и привилегии пользователя.
 * </p>
 * <ul>
 *     <li>ROLE_USER — Обычный пользователь с ограниченными правами</li>
 *     <li>ROLE_MODERATOR — Модератор с дополнительными правами управления контентом</li>
 *     <li>ROLE_ADMIN — Администратор с полными правами доступа</li>
 * </ul>
 */
public enum Role {
    ROLE_USER,       // Обычный пользователь
    ROLE_MODERATOR,  // Модератор
    ROLE_ADMIN       // Администратор
}