package com.alexpyslar03.productselectorbackend.domain.dto;

import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * DTO для передачи данных при обновлении информации о рецепте.
 * <p>
 * Этот класс включает в себя данные, необходимые для обновления существующего рецепта
 * и включает валидационные аннотации для проверки значений полей
 * и аннотации Swagger для документации API.
 * </p>
 * <ul>
 *     <li>id — Уникальный идентификатор рецепта (обязательное поле)</li>
 *     <li>name — Название рецепта (не может быть пустым)</li>
 *     <li>description — Описание рецепта</li>
 *     <li>vegan — Указывает, является ли рецепт веганским</li>
 *     <li>difficultyLevel — Уровень сложности рецепта</li>
 *     <li>rating — Рейтинг рецепта</li>
 *     <li>imageUrl — URL изображения рецепта</li>
 *     <li>productIds — Список идентификаторов продуктов, связанных с рецептом</li>
 * </ul>
 */
@Data
@Schema(description = "Запрос на обновление рецепта")
public class RecipeUpdateRequest {

    /**
     * Уникальный идентификатор рецепта.
     * ID генерируется автоматически с использованием последовательности.
     */
    @Schema(description = "Уникальный идентификатор рецепта", example = "1")
    @NotNull(message = "Уникальный идентификатор рецепта не может быть пустым")
    private Long id;

    /**
     * Название рецепта.
     * Не должно быть пустым и должно содержать от 2 до 100 символов.
     */
    @Schema(description = "Название рецепта", example = "Шоколадный торт")
    @NotBlank(message = "Название рецепта не может быть пустым")
    @Size(min = 2, max = 100, message = "Название рецепта должно содержать от 2 до 100 символов")
    private String name;

    /**
     * Описание рецепта.
     * Должно содержать от 10 до 500 символов.
     */
    @Schema(description = "Описание рецепта", example = "Этот шоколадный торт очень вкусный и легкий в приготовлении.")
    @Size(min = 10, max = 500, message = "Описание рецепта должно содержать от 10 до 500 символов")
    private String description;

    /**
     * Указывает, является ли рецепт веганским.
     * По умолчанию значение - false.
     */
    @Schema(description = "Является ли рецепт веганским", example = "true")
    private boolean vegan;

    /**
     * Уровень сложности рецепта.
     * Обязательное поле.
     */
    @Schema(description = "Уровень сложности рецепта", example = "EASY")
    @NotNull(message = "Уровень сложности рецепта не может быть пустым")
    private Recipe.DifficultyLevel difficultyLevel;

    /**
     * Рейтинг рецепта.
     * Может принимать значения от 1 до 5.
     */
    @Schema(description = "Рейтинг рецепта", example = "4")
    private Long rating;

    /**
     * URL изображения рецепта.
     * Должен быть корректным URL, но необязателен для заполнения.
     */
    @Schema(description = "URL изображения рецепта", example = "http://example.com/recipe.jpg")
    private String imageUrl;

    /**
     * Список идентификаторов продуктов, связанных с рецептом.
     * Не должен быть пустым, если продукты существуют.
     */
    @Schema(description = "Список идентификаторов продуктов", example = "[1, 2, 3]")
    @NotNull(message = "Список идентификаторов продуктов не может быть пустым")
    private List<Long> productIds;
}