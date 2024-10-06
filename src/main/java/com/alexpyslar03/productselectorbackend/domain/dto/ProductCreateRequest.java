package com.alexpyslar03.productselectorbackend.domain.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO (Data Transfer Object) для передачи данных о продукте.
 * Используется для обмена данными между слоями приложения и внешними системами.
 */
@Data
public class ProductCreateRequest {

    /**
     * Название продукта.
     */
    private String name;

    /**
     * Изображение продукта в виде массива байтов.
     */
    private byte[] image;

    /**
     * Список идентификаторов рецептов, связанных с продуктом.
     */
    private List<Long> recipeIds;
}