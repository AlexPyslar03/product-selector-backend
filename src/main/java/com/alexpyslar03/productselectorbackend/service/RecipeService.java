package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.RecipeCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.RecipeUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import com.alexpyslar03.productselectorbackend.exception.EntityNotFoundException;
import com.alexpyslar03.productselectorbackend.exception.InvalidDataException;
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
 */
@Service
@RequiredArgsConstructor
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    /**
     * Создание нового рецепта.
     * Проверяет наличие имени рецепта и существование указанных продуктов.
     *
     * @param request Объект запроса на создание рецепта.
     * @return CompletableFuture с созданным рецептом.
     */
    @Async
    public CompletableFuture<Recipe> create(RecipeCreateRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidDataException("Имя рецепта не может быть пустым.");
        }
        return productRepository.findAllByIdIn(request.getProductIds())
                .thenApply(products -> {
                    if (products.isEmpty()) {
                        throw new EntityNotFoundException("Указанные продукты не найдены.");
                    }
                    Recipe recipe = Recipe.builder()
                            .name(request.getName())
                            .products(products)
                            .build();
                    return recipeRepository.save(recipe);
                })
                .thenApply(recipe -> {
                    logger.info("Рецепт с ID {} успешно создан.", recipe.getId());
                    return recipe;
                });
    }

    /**
     * Получение всех рецептов.
     *
     * @return CompletableFuture со списком рецептов.
     */
    @Async
    public CompletableFuture<List<Recipe>> readAll() {
        return CompletableFuture.supplyAsync(() -> {
            List<Recipe> recipes = recipeRepository.findAll();
            if (recipes.isEmpty()) {
                throw new EntityNotFoundException("Рецепты не найдены.");
            }
            logger.info("Запрошен список всех рецептов.");
            return recipes;
        });
    }

    /**
     * Получение рецепта по ID.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture с найденным рецептом.
     */
    @Async
    public CompletableFuture<Recipe> readById(Long id) {
        return CompletableFuture.supplyAsync(() ->
                recipeRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Рецепт с идентификатором %d не найден.", id)))
        ).thenApply(recipe -> {
            logger.info("Рецепт с ID {} найден.", id);
            return recipe;
        });
    }

    /**
     * Получение рецептов по списку ID.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture с множеством найденных рецептов.
     */
    @Async
    public CompletableFuture<Set<Recipe>> readAllByIdIn(List<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Recipe> recipes = recipeRepository.findAllByIdIn(ids).join();
            if (recipes.isEmpty()) {
                throw new EntityNotFoundException("Не найдено рецептов с указанными идентификаторами.");
            }
            logger.info("Найдено {} рецептов по указанным ID.", recipes.size());
            return recipes;
        });
    }

    /**
     * Получение рецептов для определенного продукта по его ID.
     *
     * @param id Идентификатор продукта.
     * @return CompletableFuture со списком рецептов, содержащих данный продукт.
     */
    @Async
    public CompletableFuture<List<Recipe>> readByProductsId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            List<Recipe> recipes = recipeRepository.findByProductsId(id).join();
            if (recipes.isEmpty()) {
                throw new EntityNotFoundException(String.format("Рецепты для продукта с идентификатором %d не найдены.", id));
            }
            logger.info("Найдено {} рецептов для продукта с ID {}.", recipes.size(), id);
            return recipes;
        });
    }

    /**
     * Получение рецептов для продуктов по списку их ID.
     *
     * @param ids Список идентификаторов продуктов.
     * @return CompletableFuture со списком рецептов, содержащих указанные продукты.
     */
    @Async
    public CompletableFuture<List<Recipe>> readByProductsIdIn(List<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Recipe> recipes = recipeRepository.findByProductsIdIn(ids).join();
            if (recipes.isEmpty()) {
                throw new EntityNotFoundException(String.format("Рецепты для продуктов с идентификаторами %s не найдены.", ids));
            }
            logger.info("Найдено {} рецептов для продуктов с ID {}.", recipes.size(), ids);
            return recipes;
        });
    }

    /**
     * Обновление существующего рецепта.
     * Обновляет поля имени и продуктов, если они указаны в запросе.
     *
     * @param request Объект запроса на обновление рецепта.
     * @return CompletableFuture с обновленным рецептом.
     */
    @Async
    public CompletableFuture<Recipe> update(RecipeUpdateRequest request) {
        return recipeRepository.findById(request.getId())
                .map(recipe -> {
                    Recipe updatedRecipe = Recipe.builder()
                            .id(recipe.getId())
                            .name(request.getName() != null ? request.getName() : recipe.getName())
                            .products(recipe.getProducts())
                            .build();
                    return recipeRepository.save(updatedRecipe);
                })
                .map(recipe -> {
                    logger.info("Рецепт с ID {} успешно обновлен.", recipe.getId());
                    return CompletableFuture.completedFuture(recipe);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Невозможно обновить. Рецепт с идентификатором %d не найден.", request.getId())));
    }

    /**
     * Удаление рецепта по ID.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture<Void>, которое завершится после удаления.
     */
    @Async
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Невозможно удалить. Рецепт с идентификатором %d не найден.", id)));
            recipeRepository.deleteById(id);
            logger.info("Рецепт с ID {} успешно удален.", id);
            return null;
        });
    }
}
