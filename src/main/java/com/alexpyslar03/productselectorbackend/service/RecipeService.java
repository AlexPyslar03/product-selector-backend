package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    public Recipe create(RecipeDTO dto) {
        return recipeRepository.save(Recipe.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .is_vegan(dto.is_vegan())
                .difficulty_level(dto.getDifficulty_level())
                .rating(dto.getRating())
                .image(dto.getImage())
                .products(productRepository.findByIds(dto.getProductIds()))
                .build());
    }
    public List<Recipe> readAll() {
        return recipeRepository.findAll();
    }
    public Recipe readById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found - " + id));
    }
    public List<Recipe> readByIds(List<Long> ids) {
        return recipeRepository.findByIds(ids);
    }
    public List<Recipe> readByProductId(Long id) {
        return recipeRepository.findByProductsId(id);
    }
    public Recipe update(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
