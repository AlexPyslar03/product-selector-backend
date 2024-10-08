package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Репозиторий для работы с сущностями Product.
 * Интерфейс наследует JpaRepository, предоставляя стандартные CRUD операции.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Найти список продуктов по идентификатору рецепта асинхронно.
     *
     * @param id Идентификатор рецепта.
     * @return CompletableFuture со списком продуктов, связанных с указанным рецептом.
     */
    @Async
    CompletableFuture<List<Product>> findByRecipesId(Long id);

    /**
     * Найти список продуктов по списку идентификаторов рецептов асинхронно.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture со списком продуктов, связанных с указанными рецептами.
     */
    @Async
    CompletableFuture<List<Product>> findByRecipesIdIn(List<Long> ids);

    /**
     * Найти продукты по списку идентификаторов асинхронно.
     *
     * @param ids Список идентификаторов продуктов.
     * @return CompletableFuture с множеством продуктов, соответствующих указанным идентификаторам.
     */
    @Async
    CompletableFuture<Set<Product>> findAllByIdIn(List<Long> ids);
}
