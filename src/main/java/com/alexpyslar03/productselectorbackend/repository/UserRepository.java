package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
     * @return CompletableFuture с списком пользователей, соответствующих указанным идентификаторам.
     */
    @Async
    CompletableFuture<List<User>> findAllByIdIn(List<Long> ids);

    /**
     * Найти пользователя по его имени пользователя (username).
     *
     * @param username Имя пользователя.
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверить, существует ли пользователь с указанным именем пользователя (username).
     *
     * @param username Имя пользователя.
     * @return CompletableFuture с результатом проверки.
     */
    boolean existsByUsername(String username);

    /**
     * Проверить, существует ли пользователь с указанным адресом электронной почты (email).
     *
     * @param email Адрес электронной почты.
     * @return CompletableFuture с результатом проверки.
     */
    boolean existsByEmail(String email);
}