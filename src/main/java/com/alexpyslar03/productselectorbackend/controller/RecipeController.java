package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.domain.dto.RecipeCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.RecipeUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import com.alexpyslar03.productselectorbackend.exception.EntityNotFoundException;
import com.alexpyslar03.productselectorbackend.exception.InvalidDataException;
import com.alexpyslar03.productselectorbackend.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Recipe>> create(
            @Parameter(description = "DTO с данными нового рецепта", required = true)
            @RequestBody RecipeCreateRequest dto) {
        return recipeService.create(dto)
                .thenApply(recipe -> ResponseEntity.status(HttpStatus.CREATED).body(recipe))
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof InvalidDataException) {
                        return ResponseEntity.badRequest().body(null);
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
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
    public CompletableFuture<ResponseEntity<List<Recipe>>> readAll() {
        return recipeService.readAll()
                .thenApply(ResponseEntity::ok);
    }

    /**
     * Возвращает рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта.
     * @return Ответ с рецептом и статусом 200 OK.
     */
    @Operation(summary = "Получение рецепта по ID", description = "Возвращает рецепт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Recipe>> readById(
            @Parameter(description = "Идентификатор рецепта", required = true)
            @PathVariable Long id) {
        return recipeService.readById(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает набор рецептов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Ответ с набором рецептов и статусом 200 OK.
     */
    @Operation(summary = "Получение рецептов по ID", description = "Возвращает набор рецептов по указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены рецепты с указанными ID")
    })
    @GetMapping("/batch")
    public CompletableFuture<ResponseEntity<Set<Recipe>>> readAllByIdIn(
            @Parameter(description = "Список идентификаторов рецептов", required = true)
            @RequestParam List<Long> ids) {
        return recipeService.readAllByIdIn(ids)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает рецепты по идентификатору продукта.
     *
     * @param productId Идентификатор продукта.
     * @return Ответ с набором рецептов и статусом 200 OK.
     */
    @Operation(summary = "Получение рецептов по ID продукта", description = "Возвращает рецепты по указанному ID продукта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены рецепты для указанного ID продукта")
    })
    @GetMapping("/byProduct/{productId}")
    public CompletableFuture<ResponseEntity<List<Recipe>>> readByProductsId(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable Long productId) {
        return recipeService.readByProductsId(productId)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает рецепты по набору идентификаторов продуктов.
     *
     * @param productIds Список идентификаторов продуктов.
     * @return Ответ с набором рецептов и статусом 200 OK.
     */
    @Operation(summary = "Получение рецептов по ID продуктов", description = "Возвращает набор рецептов по указанным ID продуктов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор рецептов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены рецепты для указанных ID продуктов")
    })
    @GetMapping("/byProducts")
    public CompletableFuture<ResponseEntity<List<Recipe>>> readByProductsIdIn(
            @Parameter(description = "Список идентификаторов продуктов", required = true)
            @RequestParam List<Long> productIds) {
        return recipeService.readByProductsIdIn(productIds)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Обновляет данные рецепта.
     *
     * @param recipe Рецепт с обновленными данными.
     * @return Ответ с обновленным рецептом и статусом 200 OK.
     */
    @Operation(summary = "Обновление данных рецепта", description = "Обновляет данные рецепта и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления рецепта")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Recipe>> update(
            @Parameter(description = "Рецепт с обновленными данными", required = true)
            @RequestBody RecipeUpdateRequest recipe) {
        return recipeService.update(recipe)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    if (ex.getCause() instanceof InvalidDataException) {
                        return ResponseEntity.badRequest().body(null);
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Удаляет рецепт по его идентификатору.
     *
     * @param id Идентификатор рецепта.
     * @return Ответ с пустым телом и статусом 204 No Content.
     */
    @Operation(summary = "Удаление рецепта", description = "Удаляет рецепт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Рецепт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Рецепт с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Object>> delete(
            @Parameter(description = "Идентификатор рецепта", required = true)
            @PathVariable Long id) {
        return recipeService.delete(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build())
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}