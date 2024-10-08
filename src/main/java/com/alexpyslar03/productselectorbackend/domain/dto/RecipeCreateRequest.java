package com.alexpyslar03.productselectorbackend.domain.dto;

import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import lombok.Data;

import java.util.List;

/**
 * DTO (Data Transfer Object) для передачи данных о рецепте.
 * Используется для обмена данными между слоями приложения и внешними системами.
 */
@Data
public class RecipeCreateRequest {

    /**
     * Название рецепта.
     */
    private String name;

    /**
     * Описание рецепта.
     */
    private String description;

    /**
     * Флаг, указывающий, является ли рецепт веганским.
     */
    private boolean vegan;

    /**
     * Уровень сложности рецепта.
     * Использует перечисление DifficultyLevel из сущности Recipe.
     */
    private Recipe.DifficultyLevel difficultyLevel;

    /**
     * Рейтинг рецепта.
     * Может быть null, если рейтинг не установлен.
     */
    private Long rating;

    /**
     * URL изображения рецепта.
     * Ссылка на внешний ресурс, хранимая в базе данных как строка.
     */
    private String imageUrl;

    /**
     * Список идентификаторов продуктов, связанных с рецептом.
     */
    private List<Long> productIds;
}