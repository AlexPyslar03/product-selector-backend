package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.RecipeCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.RecipeUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
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
     * @param request DTO с данными нового рецепта.
     * @return Созданный рецепт.
     */
    public Recipe create(RecipeCreateRequest request) {
        Recipe recipe = Recipe.builder()
                .name(request.getName())
                .description(request.getDescription())
                .vegan(request.isVegan())
                .difficultyLevel(request.getDifficultyLevel())
                .rating(request.getRating())
                .imageUrl(request.getImageUrl())
                .products(productRepository.findAllByIdIn(request.getProductIds())) // Установка связанных продуктов
                .build();
        Recipe saveRecipe = recipeRepository.save(recipe);
        logger.info("Рецепт с ID {} успешно создан.", saveRecipe.getId());
        return saveRecipe;
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
     * @throws RuntimeException Если рецепт с указанным идентификатором не найден.
     */
    public Recipe readById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Рецепт с идентификатором %d не найден.", id)));
        logger.info("Рецепт с ID {} найден.", id);
        return recipe;
    }

    /**
     * Возвращает набор рецептов по предоставленным идентификаторам.
     * Если рецепты не найдены, выбрасывается исключение RecipeNotFoundException.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Набор рецептов с указанными идентификаторами.
     * @throws RuntimeException Если рецепты с указанными идентификаторами не найдены.
     */
    public Set<Recipe> readAllByIdIn(List<Long> ids) {
        Set<Recipe> recipes = recipeRepository.findAllByIdIn(ids);
        if (recipes.isEmpty()) {
            throw new RuntimeException("Рецепты с указанными идентификаторами не найдены.");
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
     * @throws RuntimeException Если рецепты для указанного продукта не найдены.
     */
    public List<Recipe> readByProductsId(Long id) {
        List<Recipe> recipes = recipeRepository.findByProductsId(id);
        if (recipes.isEmpty()) {
            throw new RuntimeException(String.format("Рецепты для продукта с идентификатором %d не найдены.", id));
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
     * @throws RuntimeException Если рецепты для указанных продуктов не найдены.
     */
    public List<Recipe> readByProductsIdIn(List<Long> ids) {
        List<Recipe> recipes = recipeRepository.findByProductsIdIn(ids);
        if (recipes.isEmpty()) {
            throw new RuntimeException(String.format("Рецепты для продуктов с идентификаторами %s не найдены.", ids));
        }
        logger.info("Найдено {} рецептов для продуктов с ID {}.", recipes.size(), ids);
        return recipes;
    }

    /**
     * Обновляет существующий рецепт.
     * Если рецепт с указанным идентификатором не найден, выбрасывается исключение RecipeNotFoundException.
     *
     * @param request Рецепт с обновленными данными.
     * @return Обновленный рецепт.
     * @throws RuntimeException Если рецепт с указанным идентификатором не найден.
     */
    public Recipe update(RecipeUpdateRequest request) {
        if (!recipeRepository.existsById(request.getId())) {
            throw new RuntimeException(String.format("Невозможно обновить. Рецепт с идентификатором %d не найден.", request.getId()));
        }
        Recipe recipe = Recipe.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .vegan(request.isVegan())
                .difficultyLevel(request.getDifficultyLevel())
                .rating(request.getRating())
                .imageUrl(request.getImageUrl())
                .products(productRepository.findAllByIdIn(request.getProductIds())) // Установка связанных продуктов
                .build();
        Recipe saveRecipe = recipeRepository.save(recipe);
        logger.info("Рецепт с ID {} успешно обновлен.", saveRecipe.getId());
        return saveRecipe;
    }

    /**
     * Удаляет рецепт по его идентификатору.
     * Если рецепт с указанным идентификатором не найден, выбрасывается исключение RecipeNotFoundException.
     *
     * @param id Идентификатор рецепта для удаления.
     * @throws RuntimeException Если рецепт с указанным идентификатором не найден.
     */
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException(String.format("Невозможно удалить. Рецепт с идентификатором %d не найден.", id));
        }
        recipeRepository.deleteById(id);
        logger.info("Рецепт с ID {} успешно удален.", id);
    }
}