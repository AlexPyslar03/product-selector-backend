package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public Recipe create(RecipeDTO dto) {
        return recipeRepository.save(Recipe.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .is_vegan(dto.is_vegan())
                .difficulty_level(dto.getDifficulty_level())
                .rating(dto.getRating())
                .image(dto.getImage())
                .build());
    }
    public List<Recipe> readAll() {
        return recipeRepository.findAll();
    }
    public Recipe readById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found - " + id));
    }
    public Recipe update(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
