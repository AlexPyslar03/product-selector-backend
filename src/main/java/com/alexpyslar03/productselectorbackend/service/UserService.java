package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.dto.UserCreateRequest;
import com.alexpyslar03.productselectorbackend.domain.dto.UserUpdateRequest;
import com.alexpyslar03.productselectorbackend.domain.entity.Role;
import com.alexpyslar03.productselectorbackend.domain.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Сервисный класс для работы с пользователями.
 * Содержит методы для создания, чтения, обновления и удаления пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    /**
     * Создает нового пользователя на основе предоставленного DTO и сохраняет его в репозитории.
     *
     * @param request DTO с данными нового пользователя.
     * @return CompletableFuture с созданным пользователем.
     */
    @Async
    public CompletableFuture<User> create(UserCreateRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .birthDate(request.getBirthDate())
                    .registrationDate(LocalDate.from(LocalDateTime.now()))
                    .role(Role.ROLE_USER)
                    .build();
            User savedUser = userRepository.save(user);
            logger.info("Пользователь с ID {} успешно создан.", savedUser.getId());
            return savedUser;
        });
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return CompletableFuture со списком пользователей.
     */
    @Async
    public CompletableFuture<List<User>> readAll() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> users = userRepository.findAll();
            logger.info("Запрошен список всех пользователей.");
            return users;
        });
    }

    /**
     * Возвращает пользователя по его идентификатору.
     * Если пользователь не найден, выбрасывается исключение RuntimeException.
     *
     * @param id Идентификатор пользователя.
     * @return CompletableFuture с пользователем.
     */
    @Async
    public CompletableFuture<User> readById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(String.format("Пользователь с идентификатором %d не найден.", id)));
            logger.info("Пользователь с ID {} найден.", id);
            return user;
        });
    }

    /**
     * Возвращает список пользователей по предоставленным идентификаторам.
     * Если пользователи не найдены, выбрасывается исключение RuntimeException.
     *
     * @param ids Список идентификаторов пользователей.
     * @return CompletableFuture со списком пользователей.
     */
    @Async
    public CompletableFuture<List<User>> readAllByIdIn(List<Long> ids) {
        return userRepository.findAllByIdIn(ids)
                .thenApply(users -> {
                    if (users.isEmpty()) {
                        throw new RuntimeException("Не найдено пользователей с указанными идентификаторами.");
                    }
                    logger.info("Найдено {} пользователей по указанным ID.", users.size());
                    return users;
                });
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Обновляет существующего пользователя.
     * Если пользователь с указанным идентификатором не найден, выбрасывается исключение RuntimeException.
     *
     * @param request Пользователь с обновленными данными.
     * @return CompletableFuture с обновленным пользователем.
     */
    @Async
    public CompletableFuture<User> update(UserUpdateRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            if (!userRepository.existsById(request.getId())) {
                throw new RuntimeException(String.format("Невозможно обновить. Пользователь с идентификатором %d не найден.", request.getId()));
            }
            User user = User.builder()
                    .id(request.getId())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .birthDate(request.getBirthDate())
                    .role(request.getRole())
                    .build();
            User savedUser = userRepository.save(user);
            logger.info("Пользователь с ID {} успешно обновлен.", savedUser.getId());
            return savedUser;
        });
    }

    /**
     * Удаляет пользователя по его идентификатору.
     * Если пользователь с указанным идентификатором не найден, выбрасывается исключение RuntimeException.
     *
     * @param id Идентификатор пользователя для удаления.
     * @return CompletableFuture с пустым значением.
     */
    @Async
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            if (!userRepository.existsById(id)) {
                throw new RuntimeException(String.format("Невозможно удалить. Пользователь с идентификатором %d не найден.", id));
            }
            userRepository.deleteById(id);
            logger.info("Пользователь с ID {} успешно удален.", id);
        });
    }
}