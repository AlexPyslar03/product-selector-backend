package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.Product;
import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest // Аннотация для тестирования слоя доступа к данным с использованием JPA
@SpringJUnitConfig // Аннотация для интеграции с Spring TestContext Framework
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository; // Внедрение зависимости RecipeRepository

    @Autowired
    private ProductRepository productRepository; // Внедрение зависимости ProductRepository

    private Product product1; // Продукт 1
    private Product product2; // Продукт 2
    private Recipe recipe1; // Рецепт 1
    private Recipe recipe2; // Рецепт 2
    private Recipe recipe3; // Рецепт 3

    @BeforeEach
    public void setUp() {
        // Создание и сохранение продуктов перед каждым тестом
        product1 = productRepository.save(Product.builder()
                .name("Product 1")
                .imageUrl("http://example.com/image1.jpg")
                .recipes(new HashSet<>(Arrays.asList(recipe1, recipe2)))
                .build());

        product2 = productRepository.save(Product.builder()
                .name("Product 2")
                .imageUrl("http://example.com/image2.jpg")
                .recipes(new HashSet<>(Collections.singletonList(recipe1)))
                .build());

        // Создание и сохранение рецептов перед каждым тестом
        recipe1 = recipeRepository.save(Recipe.builder()
                .name("Recipe 1")
                .description("Description 1")
                .vegan(true)
                .difficultyLevel(Recipe.DifficultyLevel.EASY)
                .imageUrl("http://example.com/image1.jpg")
                .build());

        recipe2 = recipeRepository.save(Recipe.builder()
                .name("Recipe 2")
                .description("Description 2")
                .vegan(false)
                .difficultyLevel(Recipe.DifficultyLevel.MEDIUM)
                .imageUrl("http://example.com/image2.jpg")
                .build());

        recipe3 = recipeRepository.save(Recipe.builder()
                .name("Recipe 3")
                .description("Description 3")
                .vegan(true)
                .difficultyLevel(Recipe.DifficultyLevel.HARD)
                .imageUrl("http://example.com/image3.jpg")
                .build());

        // Установка продуктов для каждого рецепта
        recipe1.setProducts(new HashSet<>(Arrays.asList(product1, product2)));
        recipe2.setProducts(new HashSet<>(Collections.singletonList(product1)));
        recipe3.setProducts(new HashSet<>(Collections.singletonList(product2)));

        // Сохранение всех рецептов
        recipeRepository.saveAll(Arrays.asList(recipe1, recipe2, recipe3));
    }

    /**
     * Тестирование метода findByProductsId для проверки извлечения рецептов по идентификатору продукта.
     */
    @Test
    public void testFindByProductsId() throws Exception {
        CompletableFuture<List<Recipe>> futureRecipes = recipeRepository.findByProductsId(product1.getId());

        // Ожидание завершения выполнения асинхронного метода
        List<Recipe> recipes = futureRecipes.get();

        assertNotNull(recipes); // Проверка, что список рецептов не равен null
        assertEquals(2, recipes.size()); // Проверка, что в списке 2 рецепта
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2)), new HashSet<>(recipes)); // Проверка совпадения рецептов
    }

    /**
     * Тестирование метода findByProductsIdIn для проверки извлечения рецептов по списку идентификаторов продуктов.
     */
    @Test
    public void testFindByProductsIdIn() throws Exception {
        CompletableFuture<List<Recipe>> futureRecipes = recipeRepository.findByProductsIdIn(Arrays.asList(product1.getId(), product2.getId()));

        // Ожидание завершения выполнения асинхронного метода
        List<Recipe> recipes = futureRecipes.get();

        assertNotNull(recipes); // Проверка, что список рецептов не равен null
        assertEquals(3, recipes.size()); // Проверка, что в списке 3 рецепта
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2, recipe3)), new HashSet<>(recipes)); // Проверка совпадения рецептов
    }

    /**
     * Тестирование метода findAllByIdIn для проверки извлечения рецептов по списку идентификаторов рецептов.
     */
    @Test
    public void testFindAllByIdIn() throws Exception {
        CompletableFuture<Set<Recipe>> futureRecipes = recipeRepository.findAllByIdIn(Arrays.asList(recipe1.getId(), recipe2.getId()));

        // Ожидание завершения выполнения асинхронного метода
        Set<Recipe> recipes = futureRecipes.get();

        assertNotNull(recipes); // Проверка, что множество рецептов не равно null
        assertEquals(2, recipes.size()); // Проверка, что в множестве 2 рецепта
        assertEquals(new HashSet<>(Arrays.asList(recipe1, recipe2)), recipes); // Проверка совпадения рецептов
    }
}