package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.exception.RecipeNotFoundException;
import com.alexpyslar03.productselectorbackend.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Контроллер для работы с рецептами.
 * Предоставляет endpoint'ы для создания, чтения, обновления и удаления рецептов.
 */
@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * Создает новый рецепт.
     *
     * @param dto DTO с данными нового рецепта.
     * @return Ответ с созданным рецептом и статусом 201 Created.
     */
    @Operation(summary = "Создание нового рецепта", description = "Создает новый рецепт и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Рецепт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания рецепта")
    })
    @PostMapping
    public ResponseEntity<Recipe> create(
            @Parameter(description = "DTO с данными нового рецепта", required = true) @RequestBody RecipeDTO dto) {
        Recipe createdRecipe = recipeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    /**
     * Возвращает список всех рецептов.
     *
     * @return Ответ со списком всех рецептов и статусом 200 OK.
     */
    @Operation(summary = "Получение списка всех рецептов", description = "Возвращает список всех рецептов в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список рецептов успешно возвращен")
    })
    @GetMapping
    public ResponseEntity<List<Recipe>> readAll() {
        List<Recipe> recipes = recipeService.readAll();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Возвращает рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта.
     * @return Ответ с рецептом и статусом 200 OK.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    @Operation(summary = "Получение рецепта по ID", description = "Возвращает рецепт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> readById(
            @Parameter(description = "Идентификатор рецепта", required = true) @PathVariable Long id) {
        Recipe recipe = recipeService.readById(id);
        return ResponseEntity.ok(recipe);
    }

    /**
     * Возвращает набор рецептов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Ответ с набором рецептов и статусом 200 OK.
     * @throws RecipeNotFoundException Если ни один из рецептов с указанными идентификаторами не найден.
     */
    @Operation(summary = "Получение рецептов по ID", description = "Возвращает набор рецептов по указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены рецепты с указанными ID")
    })
    @GetMapping("/batch")
    public ResponseEntity<Set<Recipe>> readByIds(
            @Parameter(description = "Список идентификаторов рецептов", required = true) @RequestParam List<Long> ids) {
        Set<Recipe> recipes = recipeService.readAllByIdIn(ids);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Возвращает список рецептов по идентификатору продукта.
     *
     * @param id Идентификатор продукта.
     * @return Ответ со списком рецептов и статусом 200 OK.
     * @throws RecipeNotFoundException Если рецепты для указанного продукта не найдены.
     */
    @Operation(summary = "Получение рецептов по ID продукта", description = "Возвращает список рецептов по указанному ID продукта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Рецепты для указанного продукта не найдены")
    })
    @GetMapping("/product/{id}")
    public ResponseEntity<List<Recipe>> readByProductId(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable Long id) {
        List<Recipe> recipes = recipeService.readByProductId(id);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Возвращает список рецептов по списку идентификаторов продуктов.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Ответ со списком рецептов и статусом 200 OK.
     * @throws RecipeNotFoundException Если рецепты для указанных продуктов не найдены.
     */
    @Operation(summary = "Получение рецептов по списку ID продуктов", description = "Возвращает список рецептов по списку идентификаторов продуктов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Рецепты для указанных продуктов не найдены")
    })
    @GetMapping("/product/batch")
    public ResponseEntity<List<Recipe>> readByProductIdIn(
            @Parameter(description = "Список идентификаторов продуктов", required = true) @RequestParam List<Long> ids) {
        List<Recipe> recipes = recipeService.readByProductIdIn(ids);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Обновляет данные рецепта.
     *
     * @param recipe Рецепт с обновленными данными.
     * @return Ответ с обновленным рецептом и статусом 200 OK.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    @Operation(summary = "Обновление данных рецепта", description = "Обновляет данные рецепта и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден")
    })
    @PutMapping
    public ResponseEntity<Recipe> update(
            @Parameter(description = "Рецепт с обновленными данными", required = true) @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.update(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    /**
     * Удаляет рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта для удаления.
     * @return Ответ со статусом 204 No Content.
     * @throws RecipeNotFoundException Если рецепт с указанным идентификатором не найден.
     */
    @Operation(summary = "Удаление рецепта по ID", description = "Удаляет рецепт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Рецепт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор рецепта для удаления", required = true) @PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}