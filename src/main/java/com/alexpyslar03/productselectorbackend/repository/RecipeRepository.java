package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByProductsId(Long id);
    @Query("select p from Recipe p where p.id in :ids" )
    List<Recipe> findByIds(@Param("ids") List<Long> ids);
}
