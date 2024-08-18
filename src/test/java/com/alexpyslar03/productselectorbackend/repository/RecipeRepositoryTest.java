package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.entity.Recipe.DifficultyLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    public void setUp() {
        // Создаем и сохраняем продукты
        product1 = productRepository.save(Product.builder().name("Product 1").build());
        product2 = productRepository.save(Product.builder().name("Product 2").build());

        // Создаем и сохраняем рецепты
        recipe1 = recipeRepository.save(Recipe.builder()
                .name("Recipe 1")
                .description("Description 1")
                .vegan(true)
                .difficultyLevel(DifficultyLevel.EASY)
                .rating(5L)
                .products(new HashSet<>(Arrays.asList(product1)))
                .build());

        recipe2 = recipeRepository.save(Recipe.builder()
                .name("Recipe 2")
                .description("Description 2")
                .vegan(false)
                .difficultyLevel(DifficultyLevel.MEDIUM)
                .rating(3L)
                .products(new HashSet<>(Arrays.asList(product2)))
                .build());
    }

    @Test
    public void testFindByProductsId() {
        List<Recipe> recipes = recipeRepository.findByProductsId(product1.getId());

        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        assertEquals(recipe1.getName(), recipes.get(0).getName());
    }

    @Test
    public void testFindByProductsIdNoMatch() {
        List<Recipe> recipes = recipeRepository.findByProductsId(Long.MAX_VALUE);

        assertNotNull(recipes);
        assertTrue(recipes.isEmpty());
    }

    @Test
    public void testFindAllByIdIn() {
        Set<Recipe> recipes = recipeRepository.findAllByIdIn(Arrays.asList(recipe1.getId(), recipe2.getId()));

        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertTrue(recipes.contains(recipe1));
        assertTrue(recipes.contains(recipe2));
    }

    @Test
    public void testFindAllByIdInNoMatch() {
        Set<Recipe> recipes = recipeRepository.findAllByIdIn(Arrays.asList(Long.MAX_VALUE));

        assertNotNull(recipes);
        assertTrue(recipes.isEmpty());
    }
}