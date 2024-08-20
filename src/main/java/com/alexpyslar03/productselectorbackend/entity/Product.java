package com.alexpyslar03.productselectorbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая продукт в базе данных.
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
     * ID генерируется автоматически с использованием последовательности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Название продукта.
     * Должно быть уникальным и не может быть null.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Двоичный большой объект (BLOB), представляющий изображение продукта.
     * Хранится в базе данных как BYTEA.
     */
    @Lob
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    /**
     * Связь многие ко многим между Product и Recipe.
     * Тип загрузки - LAZY для оптимизации производительности.
     * Операции каскадирования включают PERSIST и MERGE.
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
    private Set<Recipe> recipes = new HashSet<>();
}