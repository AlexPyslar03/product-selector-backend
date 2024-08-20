package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностями User.
 * Интерфейс наследует JpaRepository, предоставляя стандартные CRUD операции.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователей по списку идентификаторов.
     *
     * @param ids Список идентификаторов пользователей.
     * @return Список пользователей, соответствующих указанным идентификаторам.
     */
    List<User> findAllByIdIn(List<Long> ids);
}