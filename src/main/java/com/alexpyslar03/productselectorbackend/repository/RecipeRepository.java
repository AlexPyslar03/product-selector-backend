package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Репозиторий для работы с сущностями Recipe.
 * Интерфейс наследует JpaRepository, предоставляя стандартные CRUD операции.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /**
     * Найти список рецептов по идентификатору продукта.
     *
     * @param id Идентификатор продукта.
     * @return Список рецептов, содержащих указанный продукт.
     */
    List<Recipe> findByProductsId(Long id);

    /**
     * Найти список рецептов по списку идентификаторов продуктов.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Список рецептов, содержащих указанные продукты.
     */
    List<Recipe> findByProductsIdIn(List<Long> ids);

    /**
     * Найти рецепты по списку идентификаторов.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Множество рецептов, соответствующих указанным идентификаторам.
     */
    Set<Recipe> findAllByIdIn(List<Long> ids);
}
