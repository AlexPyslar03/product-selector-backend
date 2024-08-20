package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@SpringJUnitConfig
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;

    @BeforeEach
    public void setUp() {
        product1 = productRepository.save(Product.builder()
                .name("Product 1")
                .build());

        product2 = productRepository.save(Product.builder()
                .name("Product 2")
                .build());

        recipe1 = recipeRepository.save(Recipe.builder()
                .name("Recipe 1")
                .description("Description 1")
                .vegan(true)
                .difficultyLevel(Recipe.DifficultyLevel.EASY)
                .build());

        recipe2 = recipeRepository.save(Recipe.builder()
                .name("Recipe 2")
                .description("Description 2")
                .vegan(false)
                .difficultyLevel(Recipe.DifficultyLevel.MEDIUM)
                .build());

        recipe3 = recipeRepository.save(Recipe.builder()
                .name("Recipe 3")
                .description("Description 3")
                .vegan(true)
                .difficultyLevel(Recipe.DifficultyLevel.HARD)
                .build());

        recipe1.setProducts(new HashSet<>(Arrays.asList(product1, product2)));
        recipe2.setProducts(new HashSet<>(Collections.singletonList(product1)));
        recipe3.setProducts(new HashSet<>(Collections.singletonList(product2)));

        recipeRepository.saveAll(Arrays.asList(recipe1, recipe2, recipe3));
    }

    @Test
    public void testFindByProductsId() {
        List<Recipe> recipes = recipeRepository.findByProductsId(product1.getId());
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2)), new HashSet<>(recipes));
    }

    @Test
    public void testFindByProductsIdIn() {
        List<Recipe> recipes = recipeRepository.findByProductsIdIn(Arrays.asList(product1.getId(), product2.getId()));
        assertNotNull(recipes);
        assertEquals(3, recipes.size());
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2, recipe3)), new HashSet<>(recipes));
    }

    @Test
    public void testFindAllByIdIn() {
        Set<Recipe> recipes = recipeRepository.findAllByIdIn(Arrays.asList(recipe1.getId(), recipe2.getId()));
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2)), recipes);
    }
}