package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.exception.RecipeNotFoundException;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
     * @param dto DTO с данными нового рецепта.
     * @return Созданный рецепт.
     */
    public Recipe create(RecipeDTO dto) {
        Recipe recipe = Recipe.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .vegan(dto.isVegan())
                .difficultyLevel(dto.getDifficultyLevel())
                .rating(dto.getRating())
                .image(dto.getImage())
                .products(productRepository.findAllByIdIn(dto.getProductIds())) // Установка связанных продуктов
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        logger.info("Рецепт с ID {} успешно создан.", savedRecipe.getId());
        return savedRecipe;
    }

    /**
     * Возвращает список всех рецептов.
     *
     * @return Список всех рецептов.
     */
    public List<Recipe> readAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        logger.info("Запрошен список всех рецептов.");
        return recipes;
    }

    /**
     * Возвращает рецепт по его идентификатору.
     * Если рецепт не найден, выбрасывается исключение RecipeNotFoundException.
     *
     * @param id Идентификатор рецепта.
     * @return Рецепт с указанным идентификатором.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    public Recipe readById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(String.format("Рецепт с идентификатором %d не найден.", id)));
        logger.info("Рецепт с ID {} найден.", id);
        return recipe;
    }

    /**
     * Возвращает набор рецептов по предоставленным идентификаторам.
     * Если рецепты не найдены, выбрасывается исключение RecipeNotFoundException.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Набор рецептов с указанными идентификаторами.
     * @throws RecipeNotFoundException Если рецепты с указанными идентификаторами не найдены.
     */
    public Set<Recipe> readAllByIdIn(List<Long> ids) {
        Set<Recipe> recipes = recipeRepository.findAllByIdIn(ids);
        if (recipes.isEmpty()) {
            throw new RecipeNotFoundException("Рецепты с указанными идентификаторами не найдены.");
        }
        logger.info("Найдено {} рецептов по указанным ID.", recipes.size());
        return recipes;
    }

    /**
     * Возвращает список рецептов по идентификатору продукта.
     * Если рецепты не найдены, выбрасывается исключение RecipeNotFoundException.
     *
     * @param id Идентификатор продукта.
     * @return Список рецептов, содержащих указанный продукт.
     * @throws RecipeNotFoundException Если рецепты для указанного продукта не найдены.
     */
    public List<Recipe> readByProductId(Long id) {
        List<Recipe> recipes = recipeRepository.findByProductsId(id);
        if (recipes.isEmpty()) {
            throw new RecipeNotFoundException(String.format("Рецепты для продукта с идентификатором %d не найдены.", id));
        }
        logger.info("Найдено {} рецептов для продукта с ID {}.", recipes.size(), id);
        return recipes;
    }

    /**
     * Возвращает список рецептов по списку идентификаторов продуктов.
     * Если рецепты не найдены, выбрасывается исключение RecipeNotFoundException.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Список рецептов, содержащих указанные продукты.
     * @throws RecipeNotFoundException Если рецепты для указанных продуктов не найдены.
     */
    public List<Recipe> readByProductIdIn(List<Long> ids) {
        List<Recipe> recipes = recipeRepository.findByProductsIdIn(ids);
        if (recipes.isEmpty()) {
            throw new RecipeNotFoundException(String.format("Рецепты для продуктов с идентификаторами %s не найдены.", ids));
        }
        logger.info("Найдено {} рецептов для продуктов с ID {}.", recipes.size(), ids);
        return recipes;
    }

    /**
     * Обновляет существующий рецепт.
     * Если рецепт с указанным идентификатором не найден, выбрасывается исключение RecipeNotFoundException.
     *
     * @param recipe Рецепт с обновленными данными.
     * @return Обновленный рецепт.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    public Recipe update(Recipe recipe) {
        if (!recipeRepository.existsById(recipe.getId())) {
            throw new RecipeNotFoundException(String.format("Невозможно обновить. Рецепт с идентификатором %d не найден.", recipe.getId()));
        }
        Recipe updatedRecipe = recipeRepository.save(recipe);
        logger.info("Рецепт с ID {} успешно обновлен.", recipe.getId());
        return updatedRecipe;
    }

    /**
     * Удаляет рецепт по его идентификатору.
     * Если рецепт с указанным идентификатором не найден, выбрасывается исключение RecipeNotFoundException.
     *
     * @param id Идентификатор рецепта для удаления.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException(String.format("Невозможно удалить. Рецепт с идентификатором %d не найден.", id));
        }
        recipeRepository.deleteById(id);
        logger.info("Рецепт с ID {} успешно удален.", id);
    }
}