package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.ProductCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.ProductUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Product;
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
 * Сервисный класс для работы с продуктами.
 * Содержит методы для создания, чтения, обновления и удаления продуктов.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;

    /**
     * Создает новый продукт на основе предоставленного DTO и сохраняет его в репозитории.
     *
     * @param request DTO с данными нового продукта.
     * @return CompletableFuture с созданным продуктом.
     */
    @Async
    public CompletableFuture<Product> create(ProductCreateRequest request) {
        return recipeRepository.findAllByIdIn(request.getRecipeIds())
                .thenCompose(recipes -> {
                    Product product = Product.builder()
                            .name(request.getName())
                            .imageUrl(request.getImageUrl())
                            .recipes(recipes) // Передаем уже загруженные рецепты
                            .build();
                    return CompletableFuture.completedFuture(productRepository.save(product));
                })
                .thenApply(product -> {
                    logger.info("Продукт с ID {} успешно создан.", product.getId());
                    return product;
                });
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return CompletableFuture с списком всех продуктов.
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
     * Возвращает продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return CompletableFuture с продуктом.
     */
    @Async
    public CompletableFuture<Product> readById(Long id) {
        return CompletableFuture.supplyAsync(() ->
                productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Продукт с идентификатором %d не найден.", id)))
        ).thenApply(product -> {
            logger.info("Продукт с ID {} найден.", id);
            return product;
        });
    }

    /**
     * Возвращает набор продуктов по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов продуктов.
     * @return CompletableFuture с набором продуктов.
     */
    @Async
    public CompletableFuture<Set<Product>> readAllByIdIn(List<Long> ids) {
        return productRepository.findAllByIdIn(ids)
                .thenApply(products -> {
                    if (products.isEmpty()) {
                        throw new RuntimeException("Не найдено продуктов с указанными идентификаторами.");
                    }
                    logger.info("Найдено {} продуктов по указанным ID.", products.size());
                    return products;
                });
    }

    /**
     * Возвращает список продуктов по идентификатору рецепта.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture с списком продуктов.
     */
    @Async
    public CompletableFuture<List<Product>> readByRecipesId(Long id) {
        return productRepository.findByRecipesId(id)
                .thenApply(products -> {
                    if (products.isEmpty()) {
                        throw new RuntimeException(String.format("Продукты для рецепта с идентификатором %d не найдены.", id));
                    }
                    logger.info("Найдено {} продуктов для рецепта с ID {}.", products.size(), id);
                    return products;
                });
    }

    /**
     * Возвращает список продуктов по списку идентификаторов рецептов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture с списком продуктов.
     */
    @Async
    public CompletableFuture<List<Product>> readByRecipesIdIn(List<Long> ids) {
        return productRepository.findByRecipesIdIn(ids)
                .thenApply(products -> {
                    if (products.isEmpty()) {
                        throw new RuntimeException(String.format("Продукты для рецептов с идентификаторами %s не найдены.", ids));
                    }
                    logger.info("Найдено {} продуктов для рецептов с ID {}.", products.size(), ids);
                    return products;
                });
    }

    /**
     * Обновляет существующий продукт.
     *
     * @param request Продукт с обновленными данными.
     * @return CompletableFuture с обновленным продуктом.
     */
    @Async
    public CompletableFuture<Product> update(ProductUpdateRequest request) {
        if (!productRepository.existsById(request.getId())) {
            throw new RuntimeException(String.format("Невозможно обновить. Продукт с идентификатором %d не найден.", request.getId()));
        }
        return recipeRepository.findAllByIdIn(request.getRecipeIds())
                .thenCompose(recipes -> {
                    Product product = Product.builder()
                            .id(request.getId())
                            .name(request.getName())
                            .imageUrl(request.getImageUrl())
                            .recipes(recipes)
                            .build();
                    return CompletableFuture.completedFuture(productRepository.save(product));
                })
                .thenApply(product -> {
                    logger.info("Продукт с ID {} успешно обновлен.", product.getId());
                    return product;
                });
    }


    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта для удаления.
     * @return CompletableFuture<Void> с пустым значением.
     */
    @Async
    public CompletableFuture<Void> delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException(String.format("Невозможно удалить. Продукт с идентификатором %d не найден.", id));
        }
        return CompletableFuture.runAsync(() -> {
            productRepository.deleteById(id); // Удаление продукта
            logger.info("Продукт с ID {} успешно удален.", id);
        });
    }

}