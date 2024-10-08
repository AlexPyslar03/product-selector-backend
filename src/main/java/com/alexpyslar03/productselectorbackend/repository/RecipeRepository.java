package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Репозиторий для работы с сущностями Recipe.
 * Интерфейс наследует JpaRepository, предоставляя стандартные CRUD операции
 * и методы для асинхронного поиска рецептов по идентификаторам продуктов и рецептов.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Асинхронно найти список рецептов по идентификатору продукта.
     *
     * @param id Идентификатор продукта.
     * @return CompletableFuture с результатом списка рецептов, содержащих указанный продукт.
     */
    @Async
    CompletableFuture<List<Recipe>> findByProductsId(Long id);

    /**
     * Асинхронно найти список рецептов по списку идентификаторов продуктов.
     *
     * @param ids Список идентификаторов продуктов.
     * @return CompletableFuture с результатом списка рецептов, содержащих указанные продукты.
     */
    @Async
    CompletableFuture<List<Recipe>> findByProductsIdIn(List<Long> ids);

    /**
     * Асинхронно найти рецепты по списку идентификаторов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return CompletableFuture с результатом множества рецептов, соответствующих указанным идентификаторам.
     */
    @Async
    CompletableFuture<Set<Recipe>> findAllByIdIn(List<Long> ids);
}