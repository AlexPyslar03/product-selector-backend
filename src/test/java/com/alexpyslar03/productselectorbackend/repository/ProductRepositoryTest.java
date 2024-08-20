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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe recipe1;
    private Recipe recipe2;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    public void setUp() {
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

        product1 = productRepository.save(Product.builder()
                .name("Product 1")
                .recipes(new HashSet<>(Arrays.asList(recipe1, recipe2)))
                .build());

        product2 = productRepository.save(Product.builder()
                .name("Product 2")
                .recipes(new HashSet<>(Collections.singletonList(recipe1)))
                .build());

        product3 = productRepository.save(Product.builder()
                .name("Product 3")
                .recipes(new HashSet<>(Collections.singletonList(recipe2)))
                .build());
    }

    @Test
    public void testFindByRecipesId() {
        List<Product> products = productRepository.findByRecipesId(recipe1.getId());
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(new HashSet<>(Arrays.asList(product1, product2)), new HashSet<>(products));
    }

    @Test
    public void testFindByRecipesIdIn() {
        List<Product> products = productRepository.findByRecipesIdIn(Arrays.asList(recipe1.getId(), recipe2.getId()));
        assertNotNull(products);
        assertEquals(3, products.size());
        assertEquals(new HashSet<>(Arrays.asList(product1, product2, product3)), new HashSet<>(products));
    }

    @Test
    public void testFindAllByIdIn() {
        Set<Product> products = productRepository.findAllByIdIn(Arrays.asList(product1.getId(), product2.getId()));
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(new HashSet<>(Arrays.asList(product1, product2)), products);
    }
}