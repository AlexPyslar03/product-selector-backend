package com.alexpyslar03.productselectorbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая рецепт в базе данных.
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
     * ID генерируется автоматически с использованием последовательности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipe_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Название рецепта.
     * Не может быть null и должно быть уникальным.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Описание рецепта.
     * Не может быть null.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Флаг, указывающий, является ли рецепт веганским.
     * Не может быть null.
     */
    @Column(name = "is_vegan", nullable = false)
    private boolean vegan;

    /**
     * Уровень сложности рецепта.
     * Хранится как строковое значение в базе данных.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevel difficultyLevel;

    /**
     * Рейтинг рецепта.
     * Может быть null, если рейтинг не установлен.
     */
    @Column(name = "rating")
    private Long rating;

    /**
     * Двоичный большой объект (BLOB), представляющий изображение рецепта.
     * Хранится в базе данных как BYTEA.
     */
    @Lob
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    /**
     * Связь многие ко многим между Recipe и Product.
     * Тип загрузки - LAZY для оптимизации производительности.
     * Операции каскадирования включают PERSIST, MERGE и REFRESH.
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
    private Set<Product> products = new HashSet<>();

    /**
     * Перечисление уровней сложности рецепта.
     */
    public enum DifficultyLevel {
        EASY,    // Легкий
        MEDIUM,  // Средний
        HARD     // Сложный
    }
}