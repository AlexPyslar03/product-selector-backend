package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
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
                .vegan(dto.isVegan())
                .difficultyLevel(dto.getDifficultyLevel())
                .rating(dto.getRating())
                .image(dto.getImage())
                .products(productRepository.findAllByIdIn(dto.getProductIds()))
                .build());
    }

    public List<Recipe> readAll() {
        return recipeRepository.findAll();
    }

    public Recipe readById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found - " + id));
    }

    public Set<Recipe> readAllByIdIn(List<Long> ids) {
        return recipeRepository.findAllByIdIn(ids);
    }

    public List<Recipe> readByProductId(Long id) {
        return recipeRepository.findByProductsId(id);
    }

    public Recipe update(Recipe recipe) {
        if (!recipeRepository.existsById(recipe.getId())) {
            throw new RuntimeException("Recipe not found - " + recipe.getId());
        }
        return recipeRepository.save(recipe);
    }

    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found - " + id);
        }
        recipeRepository.deleteById(id);
    }
}
