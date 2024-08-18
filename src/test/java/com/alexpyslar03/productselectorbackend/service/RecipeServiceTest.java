package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.entity.Recipe.DifficultyLevel;
import com.alexpyslar03.productselectorbackend.exception.RecipeNotFoundException;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRecipe() {
        RecipeDTO dto = new RecipeDTO();
        dto.setName("Test Recipe");
        dto.setDescription("Test Description");
        dto.setVegan(true);
        dto.setDifficultyLevel(DifficultyLevel.MEDIUM);
        dto.setRating(5L);
        dto.setImage(new byte[]{1, 2, 3});
        dto.setProductIds(Arrays.asList(1L, 2L));

        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAllByIdIn(anyList())).thenReturn(new HashSet<>(Arrays.asList(product1, product2)));

        Recipe recipe = Recipe.builder()
                .id(1L)
                .name(dto.getName())
                .description(dto.getDescription())
                .vegan(dto.isVegan())
                .difficultyLevel(dto.getDifficultyLevel())
                .rating(dto.getRating())
                .image(dto.getImage())
                .products(new HashSet<>(Arrays.asList(product1, product2)))
                .build();

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe createdRecipe = recipeService.create(dto);

        assertNotNull(createdRecipe);
        assertEquals(dto.getName(), createdRecipe.getName());
        assertEquals(dto.getDescription(), createdRecipe.getDescription());
        assertEquals(dto.isVegan(), createdRecipe.isVegan());
        assertEquals(dto.getDifficultyLevel(), createdRecipe.getDifficultyLevel());
        assertEquals(dto.getRating(), createdRecipe.getRating());
        assertArrayEquals(dto.getImage(), createdRecipe.getImage());
    }

    @Test
    public void testReadAllRecipes() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> recipes = recipeService.readAll();

        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertTrue(recipes.contains(recipe1));
        assertTrue(recipes.contains(recipe2));
    }

    @Test
    public void testReadRecipeById() {
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe foundRecipe = recipeService.readById(1L);

        assertNotNull(foundRecipe);
        assertEquals(recipe, foundRecipe);
    }

    @Test
    public void testReadRecipeByIdNotFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.readById(1L);
        });

        assertEquals("Recipe not found - 1", thrown.getMessage());
    }

    @Test
    public void testReadAllByIdIn() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        when(recipeRepository.findAllByIdIn(anyList())).thenReturn(new HashSet<>(Arrays.asList(recipe1, recipe2)));

        Set<Recipe> recipes = recipeService.readAllByIdIn(Arrays.asList(1L, 2L));

        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertTrue(recipes.contains(recipe1));
        assertTrue(recipes.contains(recipe2));
    }

    @Test
    public void testReadAllByIdInNotFound() {
        when(recipeRepository.findAllByIdIn(anyList())).thenReturn(new HashSet<>());

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.readAllByIdIn(Arrays.asList(1L, 2L));
        });

        assertEquals("No recipes found for the provided IDs.", thrown.getMessage());
    }

    @Test
    public void testReadByProductId() {
        Recipe recipe = new Recipe();
        when(recipeRepository.findByProductsId(anyLong())).thenReturn(Arrays.asList(recipe));

        List<Recipe> recipes = recipeService.readByProductId(1L);

        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertTrue(recipes.contains(recipe));
    }

    @Test
    public void testReadByProductIdNotFound() {
        when(recipeRepository.findByProductsId(anyLong())).thenReturn(Arrays.asList());

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.readByProductId(1L);
        });

        assertEquals("No recipes found for the provided product ID - 1", thrown.getMessage());
    }

    @Test
    public void testUpdateRecipe() {
        Recipe recipe = new Recipe();
        when(recipeRepository.existsById(anyLong())).thenReturn(true);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe updatedRecipe = recipeService.update(recipe);

        assertNotNull(updatedRecipe);
        assertEquals(recipe, updatedRecipe);
    }

    @Test
    public void testUpdateRecipeNotFound() {
        Recipe recipe = new Recipe();
        recipe.setId(1L); // Задаем id для проверки
        when(recipeRepository.existsById(anyLong())).thenReturn(false);

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.update(recipe);
        });

        assertEquals("Recipe not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testDeleteRecipe() {
        when(recipeRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> recipeService.delete(1L));
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRecipeNotFound() {
        when(recipeRepository.existsById(anyLong())).thenReturn(false);

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.delete(1L);
        });

        assertEquals("Recipe not found with id: 1", thrown.getMessage());
    }
}