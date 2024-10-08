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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository; // Внедрение зависимости ProductRepository

    @Autowired
    private RecipeRepository recipeRepository; // Внедрение зависимости RecipeRepository

    private Recipe recipe1; // Рецепт 1
    private Recipe recipe2; // Рецепт 2
    private Product product1; // Продукт 1
    private Product product2; // Продукт 2
    private Product product3; // Продукт 3

    @BeforeEach
    public void setUp() {
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

        // Создание и сохранение продуктов перед каждым тестом
        product1 = productRepository.save(Product.builder()
                .name("Product 1")
                .imageUrl("http://example.com/image1.jpg") // Убедитесь, что добавили значение для imageUrl
                .recipes(new HashSet<>(Arrays.asList(recipe1, recipe2)))
                .build());

        product2 = productRepository.save(Product.builder()
                .name("Product 2")
                .imageUrl("http://example.com/image2.jpg") // Убедитесь, что добавили значение для imageUrl
                .recipes(new HashSet<>(Collections.singletonList(recipe1)))
                .build());

        product3 = productRepository.save(Product.builder()
                .name("Product 3")
                .imageUrl("http://example.com/image3.jpg") // Убедитесь, что добавили значение для imageUrl
                .recipes(new HashSet<>(Collections.singletonList(recipe2)))
                .build());
    }

    /**
     * Тестирование метода findByRecipesId для проверки извлечения продуктов по идентификатору рецепта.
     */
    @Test
    public void testFindByRecipesId() {
        CompletableFuture<List<Product>> productsFuture = productRepository.findByRecipesId(recipe1.getId());

        // Ожидание завершения выполнения асинхронного метода
        List<Product> products = productsFuture.join();

        assertNotNull(products); // Проверка, что список продуктов не равен null
        assertEquals(2, products.size()); // Проверка, что в списке 2 продукта
        assertEquals(new HashSet<>(Arrays.asList(product1, product2)), new HashSet<>(products)); // Проверка совпадения продуктов
    }

    /**
     * Тестирование метода findByRecipesIdIn для проверки извлечения продуктов по списку идентификаторов рецептов.
     */
    @Test
    public void testFindByRecipesIdIn() {
        CompletableFuture<List<Product>> productsFuture = productRepository.findByRecipesIdIn(Arrays.asList(recipe1.getId(), recipe2.getId()));

        // Ожидание завершения выполнения асинхронного метода
        List<Product> products = productsFuture.join();

        assertNotNull(products); // Проверка, что список продуктов не равен null
        assertEquals(3, products.size()); // Проверка, что в списке 3 продукта
        assertEquals(new HashSet<>(Arrays.asList(product1, product2, product3)), new HashSet<>(products)); // Проверка совпадения продуктов
    }

    /**
     * Тестирование метода findAllByIdIn для проверки извлечения продуктов по списку идентификаторов продуктов.
     */
    @Test
    public void testFindAllByIdIn() {
        CompletableFuture<Set<Product>> productsFuture = productRepository.findAllByIdIn(Arrays.asList(product1.getId(), product2.getId()));

        // Ожидание завершения выполнения асинхронного метода
        Set<Product> products = productsFuture.join();

        assertNotNull(products); // Проверка, что множество продуктов не равно null
        assertEquals(2, products.size()); // Проверка, что в множестве 2 продукта
        assertEquals(new HashSet<>(Arrays.asList(product1, product2)), products); // Проверка совпадения продуктов
    }
}