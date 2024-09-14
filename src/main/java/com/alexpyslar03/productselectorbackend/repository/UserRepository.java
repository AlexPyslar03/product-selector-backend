package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Найти пользователя по его имени пользователя (username).
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем пользователя.
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверить, существует ли пользователь с указанным именем пользователя (username).
     *
     * @param username Имя пользователя.
     * @return true, если пользователь с таким именем существует, иначе false.
     */
    boolean existsByUsername(String username);

    /**
     * Проверить, существует ли пользователь с указанным адресом электронной почты (email).
     *
     * @param email Адрес электронной почты.
     * @return true, если пользователь с таким email существует, иначе false.
     */
    boolean existsByEmail(String email);
}
