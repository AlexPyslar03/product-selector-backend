package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.ProductCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.ProductUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Product;
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
 * Сервисный класс для работы с продуктами.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;

    /**
     * Создание нового продукта.
     *
     * @param request Объект запроса на создание продукта.
     * @return CompletableFuture с созданным продуктом.
     * @throws InvalidDataException если имя или URL изображения пустые.
     */
    @Async
    public CompletableFuture<Product> create(ProductCreateRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidDataException("Имя продукта не может быть пустым.");
        }
        if (request.getImageUrl() == null || request.getImageUrl().isEmpty()) {
            throw new InvalidDataException("URL изображения не может быть пустым.");
        }

        return recipeRepository.findAllByIdIn(request.getRecipeIds())
                .thenApply(recipes -> {
                    Product product = Product.builder()
                            .name(request.getName())
                            .imageUrl(request.getImageUrl())
                            .recipes(recipes)
                            .build();
                    return productRepository.save(product);
                })
                .thenApply(product -> {
                    logger.info("Продукт с ID {} успешно создан.", product.getId());
                    return product;
                });
    }

    /**
     * Получение списка всех продуктов.
     *
     * @return CompletableFuture со списком продуктов.
     */
    @Async
    public CompletableFuture<List<Product>> readAll() {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> products = productRepository.findAll();
            logger.info("Запрошен список всех продуктов.");
            return products;
        });
    }

    /**
     * Получение продукта по ID.
     *
     * @param id Идентификатор продукта.
     * @return CompletableFuture с найденным продуктом.
     * @throws EntityNotFoundException если продукт не найден.
     */
    @Async
    public CompletableFuture<Product> readById(Long id) {
        return CompletableFuture.supplyAsync(() ->
                productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с идентификатором %d не найден.", id)))
        ).thenApply(product -> {
            logger.info("Продукт с ID {} найден.", id);
            return product;
        });
    }

    /**
     * Получение всех продуктов по списку ID.
     *
     * @param ids Список идентификаторов продуктов.
     * @return CompletableFuture с множеством найденных продуктов.
     * @throws EntityNotFoundException если ни один продукт не найден.
     */
    @Async
    public CompletableFuture<Set<Product>> readAllByIdIn(List<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Product> products = productRepository.findAllByIdIn(ids).join();
            if (products.isEmpty()) {
                throw new EntityNotFoundException("Не найдено продуктов с указанными идентификаторами.");
            }
            logger.info("Найдено {} продуктов по указанным ID.", products.size());
            return products;
        });
    }

    /**
     * Получение продуктов по идентификатору рецепта.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture со списком продуктов.
     * @throws EntityNotFoundException если продукты не найдены.
     */
    @Async
    public CompletableFuture<List<Product>> readByRecipesId(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> products = productRepository.findByRecipesId(id).join();
            if (products.isEmpty()) {
                throw new EntityNotFoundException(String.format("Продукты для рецепта с идентификатором %d не найдены.", id));
            }
            logger.info("Найдено {} продуктов для рецепта с ID {}.", products.size(), id);
            return products;
        });
    }

    /**
     * Получение продуктов по списку идентификаторов рецептов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture со списком продуктов.
     * @throws EntityNotFoundException если продукты не найдены.
     */
    @Async
    public CompletableFuture<List<Product>> readByRecipesIdIn(List<Long> ids) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> products = productRepository.findByRecipesIdIn(ids).join();
            if (products.isEmpty()) {
                throw new EntityNotFoundException(String.format("Продукты для рецептов с идентификаторами %s не найдены.", ids));
            }
            logger.info("Найдено {} продуктов для рецептов с ID {}.", products.size(), ids);
            return products;
        });
    }

    /**
     * Обновление существующего продукта.
     *
     * @param request Объект запроса на обновление продукта.
     * @return CompletableFuture с обновленным продуктом.
     * @throws EntityNotFoundException если продукт не найден.
     */
    @Async
    public CompletableFuture<Product> update(ProductUpdateRequest request) {
        return productRepository.findById(request.getId())
                .map(product -> {
                    Product updatedProduct = Product.builder()
                            .id(product.getId())
                            .name(request.getName() != null ? request.getName() : product.getName())
                            .imageUrl(request.getImageUrl() != null ? request.getImageUrl() : product.getImageUrl())
                            .recipes(product.getRecipes())
                            .build();
                    return productRepository.save(updatedProduct);
                })
                .map(product -> {
                    logger.info("Продукт с ID {} успешно обновлен.", product.getId());
                    return CompletableFuture.completedFuture(product);
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Невозможно обновить. Продукт с идентификатором %d не найден.", request.getId())));
    }

    /**
     * Удаление продукта по ID.
     *
     * @param id Идентификатор продукта.
     * @return CompletableFuture<Void>.
     * @throws EntityNotFoundException если продукт не найден.
     */
    @Async
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Невозможно удалить. Продукт с идентификатором %d не найден.", id)));
            productRepository.deleteById(id);
            logger.info("Продукт с ID {} успешно удален.", id);
            return null;
        });
    }
}