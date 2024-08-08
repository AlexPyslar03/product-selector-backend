package com.alexpyslar03.productselectorbackend.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RecipeDTO {
    private String name;
    private String description;
    private boolean is_vegan;
    private int difficulty_level;
    private Long rating;
    private String image;
    private List<Long> productIds;
}
