package com.alexpyslar03.productselectorbackend.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    private String name;
    private String image;
    private List<Long> recipeIds;
}
