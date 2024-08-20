package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Репозиторий для работы с сущностями Product.
 * Интерфейс наследует JpaRepository, предоставляя стандартные CRUD операции.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Найти список продуктов по идентификатору рецепта.
     *
     * @param id Идентификатор рецепта.
     * @return Список продуктов, связанных с указанным рецептом.
     */
    List<Product> findByRecipesId(Long id);

    /**
     * Найти список продуктов по списку идентификаторов рецептов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Список продуктов, связанных с указанными рецептами.
     */
    List<Product> findByRecipesIdIn(List<Long> ids);

    /**
     * Найти продукты по списку идентификаторов.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Множество продуктов, соответствующих указанным идентификаторам.
     */
    Set<Product> findAllByIdIn(List<Long> ids);
}
