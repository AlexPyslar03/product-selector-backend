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
     * URL изображения продукта.
     * Ссылка на внешний ресурс, хранимая в базе данных как строка.
     */
    private String imageUrl;

    /**
     * Список идентификаторов рецептов, связанных с продуктом.
     */
    private List<Long> recipeIds;
}