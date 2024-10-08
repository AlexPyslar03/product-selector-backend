package com.alexpyslar03.productselectorbackend.domain.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Модель рецепта.
 * <p>
 * Этот класс представляет сущность рецепта в системе и включает в себя
 * поля, необходимые для хранения информации о рецепте.
 * </p>
 * <ul>
 *     <li>id — Уникальный идентификатор рецепта</li>
 *     <li>name — Название рецепта (не может быть пустым и уникальным)</li>
 *     <li>description — Описание рецепта (не может быть пустым)</li>
 *     <li>vegan — Указывает, является ли рецепт веганским</li>
 *     <li>difficultyLevel — Уровень сложности рецепта</li>
 *     <li>rating — Рейтинг рецепта</li>
 *     <li>imageUrl — URL изображения рецепта</li>
 *     <li>products — Набор продуктов, связанных с рецептом</li>
 * </ul>
 */
@Entity
@Table(name = "recipes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    /**
     * Уникальный идентификатор рецепта.
     * Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор рецепта", example = "1")
    private Long id;

    /**
     * Название рецепта.
     * Не должно быть пустым и должно быть уникальным.
     */
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Название рецепта", example = "Шоколадный торт")
    private String name;

    /**
     * Описание рецепта.
     * Не должно быть пустым.
     */
    @Column(name = "description", nullable = false)
    @Schema(description = "Описание рецепта", example = "Этот шоколадный торт очень вкусный и легкий в приготовлении.")
    private String description;

    /**
     * Указывает, является ли рецепт веганским.
     * Обязательное поле.
     */
    @Column(name = "is_vegan", nullable = false)
    @Schema(description = "Является ли рецепт веганским", example = "true")
    private boolean vegan;

    /**
     * Уровень сложности рецепта.
     * Обязательное поле.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    @Schema(description = "Уровень сложности рецепта", example = "EASY")
    private DifficultyLevel difficultyLevel;

    /**
     * Рейтинг рецепта.
     * Может принимать значения от 1 до 5.
     */
    @Column(name = "rating")
    @Schema(description = "Рейтинг рецепта", example = "4")
    private Long rating;

    /**
     * URL изображения рецепта.
     * Не должен быть пустым и должен быть уникальным.
     */
    @Column(name = "image_url", nullable = false, unique = true)
    @Schema(description = "URL изображения рецепта", example = "http://example.com/recipe.jpg")
    private String imageUrl;

    /**
     * Набор продуктов, связанных с рецептом.
     * Используется связь многие-ко-многим с продуктами.
     */
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "products_recipes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Schema(description = "Набор продуктов, связанных с рецептом")
    private Set<Product> products = new HashSet<>();

    /**
     * Уровень сложности рецепта.
     */
    public enum DifficultyLevel {
        EASY,    // Легкий
        MEDIUM,  // Средний
        HARD     // Сложный
    }
}