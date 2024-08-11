package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select p from User p where p.id in :ids" )
    List<User> findByIds(@Param("ids") List<Long> ids);
}
