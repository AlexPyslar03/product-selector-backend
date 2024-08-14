package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> create(@RequestBody RecipeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> readAll() {
        return ResponseEntity.ok(recipeService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> readById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.readById(id));
    }

    @GetMapping("/batch")
    public ResponseEntity<Set<Recipe>> readByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(recipeService.readAllByIdIn(ids));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Recipe>> readByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.readByProductId(id));
    }

    @PutMapping
    public ResponseEntity<Recipe> update(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.update(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}