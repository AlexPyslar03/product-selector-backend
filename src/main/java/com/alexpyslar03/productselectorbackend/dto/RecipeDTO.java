package com.alexpyslar03.productselectorbackend.dto;

import com.alexpyslar03.productselectorbackend.entity.Recipe;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RecipeDTO {
    private String name;
    private String description;
    private boolean vegan;
    private Recipe.DifficultyLevel difficultyLevel;
    private Long rating;
    private byte[] image;
    private List<Long> productIds;
}
