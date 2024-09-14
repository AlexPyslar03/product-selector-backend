package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.domain.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param dto DTO с данными нового пользователя.
     * @return Сообщение о создании пользователя и сам созданный пользователь.
     */
    public User create(User dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .birthDate(dto.getBirthDate())
                .registrationDate(dto.getRegistrationDate())
                .role(dto.getRole())
                .build();
        User savedUser = userRepository.save(user);
        logger.info("Пользователь с ID {} успешно создан.", savedUser.getId());
        return savedUser;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список пользователей.
     */
    public List<User> readAll() {
        List<User> users = userRepository.findAll();
        logger.info("Запрошен список всех пользователей.");
        return users;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     * Если пользователь не найден, выбрасывается исключение UserNotFoundException.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором.
     * @throws RuntimeException Если пользователь с указанным идентификатором не найден.
     */
    public User readById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователь с идентификатором %d не найден.", id)));
        logger.info("Пользователь с ID {} найден.", id);
        return user;
    }

    /**
     * Возвращает список пользователей по предоставленным идентификаторам.
     * Если пользователи не найдены, выбрасывается исключение UserNotFoundException.
     *
     * @param ids Список идентификаторов пользователей.
     * @return Список пользователей с указанными идентификаторами.
     * @throws RuntimeException Если пользователи с указанными идентификаторами не найдены.
     */
    public List<User> readAllByIdIn(List<Long> ids) {
        List<User> users = userRepository.findAllByIdIn(ids);
        if (users.isEmpty()) {
            throw new RuntimeException("Не найдено пользователей с указанными идентификаторами.");
        }
        logger.info("Найдено {} пользователей по указанным ID.", users.size());
        return users;
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
     * Если пользователь с указанным идентификатором не найден, выбрасывается исключение UserNotFoundException.
     *
     * @param user Пользователь с обновленными данными.
     * @return Обновленный пользователь.
     * @throws RuntimeException Если пользователь с указанным идентификатором не найден.
     */
    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException(String.format("Невозможно обновить. Пользователь с идентификатором %d не найден.", user.getId()));
        }
        User updatedUser = userRepository.save(user);
        logger.info("Пользователь с ID {} успешно обновлен.", user.getId());
        return updatedUser;
    }

    /**
     * Удаляет пользователя по его идентификатору.
     * Если пользователь с указанным идентификатором не найден, выбрасывается исключение UserNotFoundException.
     *
     * @param id Идентификатор пользователя для удаления.
     * @throws RuntimeException Если пользователь с указанным идентификатором не найден.
     */
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException(String.format("Невозможно удалить. Пользователь с идентификатором %d не найден.", id));
        }
        userRepository.deleteById(id);
        logger.info("Пользователь с ID {} успешно удален.", id);
    }
}