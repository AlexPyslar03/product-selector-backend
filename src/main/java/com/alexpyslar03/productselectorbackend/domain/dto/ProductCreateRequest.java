package com.alexpyslar03.productselectorbackend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для передачи данных при создании нового продукта.
 * <p>
 * Этот класс включает в себя данные, необходимые для создания нового продукта
 * и включает валидационные аннотации для проверки значений полей
 * и аннотации Swagger для документации API.
 * </p>
 * <ul>
 *     <li>name — Название продукта (не может быть пустым)</li>
 *     <li>imageUrl — URL изображения продукта</li>
 *     <li>recipeIds — Список идентификаторов рецептов, связанных с продуктом</li>
 * </ul>
 */
@Data
@Schema(description = "Запрос на создание продукта")
public class ProductCreateRequest {

    /**
     * Название продукта.
     * Не должно быть пустым и должно содержать от 2 до 100 символов.
     */
    @Schema(description = "Название продукта", example = "Шоколадный торт")
    @NotBlank(message = "Название продукта не может быть пустым")
    @Size(min = 2, max = 100, message = "Название продукта должно содержать от 2 до 100 символов")
    private String name;

    /**
     * URL изображения продукта.
     * Должен быть корректным URL, но необязателен для заполнения.
     */
    @Schema(description = "URL изображения продукта", example = "http://example.com/image.jpg")
    private String imageUrl;

    /**
     * Список идентификаторов рецептов, связанных с продуктом.
     * Не должен быть пустым, если рецепты существуют.
     */
    @Schema(description = "Список идентификаторов рецептов", example = "[1, 2, 3]")
    @NotNull(message = "Список идентификаторов рецептов не может быть пустым")
    private List<Long> recipeIds;
}