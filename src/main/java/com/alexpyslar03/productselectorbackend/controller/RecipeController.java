package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> create(@RequestBody RecipeDTO dto) {
        return mappingResponseRecipe(recipeService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> readAll() {
        return mappingResponseListRecipe(recipeService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> readById(@PathVariable Long id) {
        return mappingResponseRecipe(recipeService.readById(id));
    }

    @GetMapping("/{ids}")
    public ResponseEntity<List<Recipe>> readByIds(@PathVariable List<Long> ids) {
        return mappingResponseListRecipe(recipeService.readByIds(ids));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Recipe>> readByProductId(@PathVariable Long id) {
        return mappingResponseListRecipe(recipeService.readByProductId(id));
    }

    @PutMapping
    public ResponseEntity<Recipe> update(@RequestBody Recipe recipe) {
        return mappingResponseRecipe(recipeService.update(recipe));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        recipeService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Recipe> mappingResponseRecipe(Recipe recipe) {
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    private ResponseEntity<List<Recipe>> mappingResponseListRecipe(List<Recipe> recipes) {
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}
