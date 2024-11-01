package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.domain.dto.ProductCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.ProductUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Product;
import com.alexpyslar03.productselectorbackend.exception.EntityNotFoundException;
import com.alexpyslar03.productselectorbackend.exception.InvalidDataException;
import com.alexpyslar03.productselectorbackend.service.ProductService;
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
 * Контроллер для работы с продуктами.
 * Предоставляет endpoint'ы для создания, чтения, обновления и удаления продуктов.
 */
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Создает новый продукт.
     *
     * @param dto DTO с данными нового продукта.
     * @return Ответ с созданным продуктом и статусом 201 Created.
     */
    @Operation(summary = "Создание нового продукта", description = "Создает новый продукт и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Продукт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания продукта")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Product>> create(
            @Parameter(description = "DTO с данными нового продукта", required = true) @RequestBody ProductCreateRequest dto) {
        return productService.create(dto)
                .thenApply(product -> ResponseEntity.status(HttpStatus.CREATED).body(product))
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof InvalidDataException) {
                        return ResponseEntity.badRequest().body(null);
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return Ответ со списком всех продуктов и статусом 200 OK.
     */
    @Operation(summary = "Получение списка всех продуктов", description = "Возвращает список всех продуктов в системе.")
    @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен")
    @GetMapping
    public CompletableFuture<ResponseEntity<List<Product>>> readAll() {
        return productService.readAll()
                .thenApply(ResponseEntity::ok);
    }

    /**
     * Возвращает продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Ответ с продуктом и статусом 200 OK.
     */
    @Operation(summary = "Получение продукта по ID", description = "Возвращает продукт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Product>> readById(
            @Parameter(description = "Идентификатор продукта", required = true)
            @PathVariable Long id) {
        return productService.readById(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает набор продуктов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Ответ с набором продуктов и статусом 200 OK.
     */
    @Operation(summary = "Получение продуктов по ID", description = "Возвращает набор продуктов по указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены продукты с указанными ID")
    })
    @GetMapping("/batch")
    public CompletableFuture<ResponseEntity<Set<Product>>> readAllByIdIn(
            @Parameter(description = "Список идентификаторов продуктов", required = true)
            @RequestParam List<Long> ids) {
        return productService.readAllByIdIn(ids)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает список продуктов по идентификатору рецепта.
     *
     * @param id Идентификатор рецепта.
     * @return Ответ со списком продуктов и статусом 200 OK.
     */
    @Operation(summary = "Получение продуктов по ID рецепта", description = "Возвращает список продуктов по указанному ID рецепта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукты для указанного рецепта не найдены")
    })
    @GetMapping("/recipe/{id}")
    public CompletableFuture<ResponseEntity<List<Product>>> readByRecipesId(
            @Parameter(description = "Идентификатор рецепта", required = true)
            @PathVariable Long id) {
        return productService.readByRecipesId(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Возвращает список продуктов по списку идентификаторов рецептов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Ответ со списком продуктов и статусом 200 OK.
     */
    @Operation(summary = "Получение продуктов по списку ID рецептов", description = "Возвращает список продуктов по списку идентификаторов рецептов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукты для указанных рецептов не найдены")
    })
    @GetMapping("/recipe/batch")
    public CompletableFuture<ResponseEntity<List<Product>>> readByRecipesIdIn(
            @Parameter(description = "Список идентификаторов рецептов", required = true)
            @RequestParam List<Long> ids) {
        return productService.readByRecipesIdIn(ids)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Обновляет данные продукта.
     *
     * @param product Продукт с обновленными данными.
     * @return Ответ с обновленным продуктом и статусом 200 OK.
     */
    @Operation(summary = "Обновление данных продукта", description = "Обновляет данные продукта и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления продукта")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Product>> update(
            @Parameter(description = "Продукт с обновленными данными", required = true)
            @RequestBody ProductUpdateRequest product) {
        return productService.update(product)
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
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Ответ с пустым телом и статусом 204 No Content.
     */
    @Operation(summary = "Удаление продукта", description = "Удаляет продукт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Продукт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CompletableFuture<ResponseEntity<Object>> delete(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable Long id) {
        return productService.delete(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build())
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof EntityNotFoundException) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}
