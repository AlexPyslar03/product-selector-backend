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
 * Модель продукта.
 * <p>
 * Этот класс представляет сущность продукта в системе и включает в себя
 * поля, необходимые для хранения информации о продукте.
 * </p>
 * <ul>
 *     <li>id — Уникальный идентификатор продукта</li>
 *     <li>name — Название продукта (не может быть пустым и уникальным)</li>
 *     <li>imageUrl — URL изображения продукта (не может быть пустым и уникальным)</li>
 *     <li>recipes — Набор рецептов, связанных с продуктом</li>
 * </ul>
 */
@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * Уникальный идентификатор продукта.
     * Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    @Schema(description = "Уникальный идентификатор продукта", example = "1")
    private Long id;

    /**
     * Название продукта.
     * Не должно быть пустым и должно быть уникальным.
     */
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Название продукта", example = "Молоко")
    private String name;

    /**
     * URL изображения продукта.
     * Не должен быть пустым и должен быть уникальным.
     */
    @Column(name = "image_url", nullable = false, unique = true)
    @Schema(description = "URL изображения продукта", example = "http://example.com/product.jpg")
    private String imageUrl;

    /**
     * Набор рецептов, связанных с продуктом.
     * Используется связь многие-ко-многим с рецептами.
     */
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "products_recipes",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @Schema(description = "Набор рецептов, связанных с продуктом")
    private Set<Recipe> recipes = new HashSet<>();
}