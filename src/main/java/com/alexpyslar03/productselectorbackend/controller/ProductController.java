package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.exception.ProductNotFoundException;
import com.alexpyslar03.productselectorbackend.service.ProductService;
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
    public ResponseEntity<Product> create(
            @Parameter(description = "DTO с данными нового продукта", required = true) @RequestBody ProductDTO dto) {
        Product createdProduct = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return Ответ со списком всех продуктов и статусом 200 OK.
     */
    @Operation(summary = "Получение списка всех продуктов", description = "Возвращает список всех продуктов в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен")
    })
    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        List<Product> products = productService.readAll();
        return ResponseEntity.ok(products);
    }

    /**
     * Возвращает продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Ответ с продуктом и статусом 200 OK.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    @Operation(summary = "Получение продукта по ID", description = "Возвращает продукт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> readById(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable Long id) {
        Product product = productService.readById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Возвращает набор продуктов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Ответ с набором продуктов и статусом 200 OK.
     * @throws ProductNotFoundException Если ни один из продуктов с указанными идентификаторами не найден.
     */
    @Operation(summary = "Получение продуктов по ID", description = "Возвращает набор продуктов по указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Набор продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены продукты с указанными ID")
    })
    @GetMapping("/batch")
    public ResponseEntity<Set<Product>> readByIds(
            @Parameter(description = "Список идентификаторов продуктов", required = true) @RequestParam List<Long> ids) {
        Set<Product> products = productService.readAllByIdIn(ids);
        return ResponseEntity.ok(products);
    }

    /**
     * Возвращает список продуктов по идентификатору рецепта.
     *
     * @param id Идентификатор рецепта.
     * @return Ответ со списком продуктов и статусом 200 OK.
     * @throws ProductNotFoundException Если продукты для указанного рецепта не найдены.
     */
    @Operation(summary = "Получение продуктов по ID рецепта", description = "Возвращает список продуктов по указанному ID рецепта.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукты для указанного рецепта не найдены")
    })
    @GetMapping("/recipe/{id}")
    public ResponseEntity<List<Product>> readByRecipeId(
            @Parameter(description = "Идентификатор рецепта", required = true) @PathVariable Long id) {
        List<Product> products = productService.readByRecipeId(id);
        return ResponseEntity.ok(products);
    }

    /**
     * Возвращает список продуктов по списку идентификаторов рецептов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Ответ со списком продуктов и статусом 200 OK.
     * @throws ProductNotFoundException Если продукты для указанных рецептов не найдены.
     */
    @Operation(summary = "Получение продуктов по списку ID рецептов", description = "Возвращает список продуктов по списку идентификаторов рецептов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Продукты для указанных рецептов не найдены")
    })
    @GetMapping("/recipe/batch")
    public ResponseEntity<List<Product>> readByRecipeIdIn(
            @Parameter(description = "Список идентификаторов рецептов", required = true) @RequestParam List<Long> ids) {
        List<Product> products = productService.readByRecipeIdIn(ids);
        return ResponseEntity.ok(products);
    }

    /**
     * Обновляет данные продукта.
     *
     * @param product Продукт с обновленными данными.
     * @return Ответ с обновленным продуктом и статусом 200 OK.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    @Operation(summary = "Обновление данных продукта", description = "Обновляет данные продукта и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден")
    })
    @PutMapping
    public ResponseEntity<Product> update(
            @Parameter(description = "Продукт с обновленными данными", required = true) @RequestBody Product product) {
        Product updatedProduct = productService.update(product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта для удаления.
     * @return Ответ со статусом 204 No Content.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    @Operation(summary = "Удаление продукта по ID", description = "Удаляет продукт по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Продукт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Продукт с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор продукта для удаления", required = true) @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}