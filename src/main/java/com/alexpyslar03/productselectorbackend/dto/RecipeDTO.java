package com.alexpyslar03.productselectorbackend.dto;

import com.alexpyslar03.productselectorbackend.entity.Recipe;
import lombok.Data;

import java.util.List;

/**
 * DTO (Data Transfer Object) для передачи данных о рецепте.
 * Используется для обмена данными между слоями приложения и внешними системами.
 */
@Data
public class RecipeDTO {

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
     * Изображение рецепта в виде массива байтов.
     */
    private byte[] image;

    /**
     * Список идентификаторов продуктов, связанных с рецептом.
     */
    private List<Long> productIds;
}