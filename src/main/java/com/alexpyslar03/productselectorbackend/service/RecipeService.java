package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.RecipeCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.RecipeUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Сервисный класс для работы с рецептами.
 * Содержит методы для создания, чтения, обновления и удаления рецептов.
 */
@Service
@RequiredArgsConstructor
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    /**
     * Создает новый рецепт на основе предоставленного DTO и сохраняет его в репозитории.
     *
     * @param request DTO с данными нового рецепта.
     * @return CompletableFuture с созданным рецептом.
     */
    @Async
    public CompletableFuture<Recipe> create(RecipeCreateRequest request) {
        return productRepository.findAllByIdIn(request.getProductIds())
                .thenCompose(products -> {
                    Recipe recipe = Recipe.builder()
                            .name(request.getName())
                            .description(request.getDescription())
                            .vegan(request.isVegan())
                            .difficultyLevel(request.getDifficultyLevel())
                            .rating(request.getRating())
                            .imageUrl(request.getImageUrl())
                            .products(products) // Передаем уже загруженные продукты
                            .build();
                    return CompletableFuture.completedFuture(recipeRepository.save(recipe));
                })
                .thenApply(recipe -> {
                    logger.info("Рецепт с ID {} успешно создан.", recipe.getId());
                    return recipe;
                });
    }

    /**
     * Возвращает список всех рецептов.
     *
     * @return CompletableFuture с списком всех рецептов.
     */
    @Async
    public CompletableFuture<List<Recipe>> readAll() {
        return CompletableFuture.supplyAsync(() -> {
            List<Recipe> recipes = recipeRepository.findAll();
            logger.info("Запрошен список всех рецептов.");
            return recipes;
        });
    }

    /**
     * Возвращает рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture с рецептом.
     */
    @Async
    public CompletableFuture<Recipe> readById(Long id) {
        return CompletableFuture.supplyAsync(() ->
                recipeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Рецепт с идентификатором %d не найден.", id)))
        ).thenApply(recipe -> {
            logger.info("Рецепт с ID {} найден.", id);
            return recipe;
        });
    }

    /**
     * Возвращает набор рецептов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture с набором рецептов.
     */
    @Async
    public CompletableFuture<Set<Recipe>> readAllByIdIn(List<Long> ids) {
        return recipeRepository.findAllByIdIn(ids)
                .thenApply(recipes -> {
                    if (recipes.isEmpty()) {
                        throw new RuntimeException("Не найдено рецептов с указанными идентификаторами.");
                    }
                    logger.info("Найдено {} рецептов по указанным ID.", recipes.size());
                    return recipes;
                });
    }

    /**
     * Обновляет существующий рецепт.
     *
     * @param request Рецепт с обновленными данными.
     * @return CompletableFuture с обновленным рецептом.
     */
    @Async
    public CompletableFuture<Recipe> update(RecipeUpdateRequest request) {
        if (!recipeRepository.existsById(request.getId())) {
            throw new RuntimeException(String.format("Невозможно обновить. Рецепт с идентификатором %d не найден.", request.getId()));
        }
        // Получаем список продуктов асинхронно
        return productRepository.findAllByIdIn(request.getProductIds())
                .thenCompose(products -> {
                    // Создание обновлённого рецепта
                    Recipe recipe = Recipe.builder()
                            .id(request.getId())
                            .name(request.getName())
                            .description(request.getDescription())
                            .vegan(request.isVegan())
                            .difficultyLevel(request.getDifficultyLevel())
                            .rating(request.getRating())
                            .imageUrl(request.getImageUrl())
                            .products(products) // Теперь products имеет тип Set<Product>
                            .build();

                    // Сохраняем рецепт асинхронно
                    return CompletableFuture.completedFuture(recipeRepository.save(recipe));
                })
                .thenApply(recipe -> {
                    logger.info("Рецепт с ID {} успешно обновлен.", recipe.getId());
                    return recipe;
                });
    }


    /**
     * Удаляет рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта для удаления.
     */
    @Async
    public CompletableFuture<Void> delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException(String.format("Невозможно удалить. Рецепт с идентификатором %d не найден.", id));
        }
        return CompletableFuture.runAsync(() -> {
            recipeRepository.deleteById(id); // Удаление рецепта
            logger.info("Рецепт с ID {} успешно удален.", id);
        });
    }

}