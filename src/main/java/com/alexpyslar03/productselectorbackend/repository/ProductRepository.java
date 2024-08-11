package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByRecipesId(Long id);
    @Query("select p from Product p where p.id in :ids" )
    List<Product> findByIds(@Param("ids") List<Long> ids);
}