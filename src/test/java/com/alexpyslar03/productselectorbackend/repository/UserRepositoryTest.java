package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.domain.entity.Role;
import com.alexpyslar03.productselectorbackend.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest // Аннотация для тестирования слоя доступа к данным с использованием JPA
@SpringJUnitConfig // Аннотация для интеграции с Spring TestContext Framework
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // Внедрение зависимости UserRepository

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        // Создание и сохранение пользователей перед каждым тестом
        user1 = userRepository.save(User.builder()
                .username("John")
                .email("john.doe@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .role(Role.ROLE_USER)
                .build());

        user2 = userRepository.save(User.builder()
                .username("Jane")
                .email("jane.doe@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .role(Role.ROLE_USER)
                .build());

        user3 = userRepository.save(User.builder()
                .username("Jim")
                .email("jim.beam@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .role(Role.ROLE_USER)
                .build());
    }

    /**
     * Тестирование метода findAllByIdIn для проверки извлечения пользователей по их идентификаторам.
     */
    @Test
    public void testFindAllByIdIn() {
        CompletableFuture<List<User>> usersFuture = userRepository.findAllByIdIn(Arrays.asList(user1.getId(), user2.getId(), user3.getId()));

        // Ожидание завершения выполнения асинхронного метода
        List<User> users = usersFuture.join();

        assertNotNull(users); // Проверка, что список пользователей не равен null
        assertEquals(3, users.size()); // Проверка, что в списке 3 пользователя
        assertEquals(new HashSet<>(Arrays.asList(user1, user2, user3)), new HashSet<>(users)); // Проверка, что пользователи совпадают
    }

    /**
     * Тестирование метода findByUsername для проверки поиска пользователя по имени пользователя.
     */
    @Test
    public void testFindByUsername() {
        User foundUser = userRepository.findByUsername(user1.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        assertNotNull(foundUser); // Проверка, что найденный пользователь не равен null
        assertEquals(user1.getUsername(), foundUser.getUsername()); // Проверка, что имена пользователей совпадают
    }

    /**
     * Тестирование метода existsByUsername для проверки существования пользователя по имени пользователя.
     */
    @Test
    public void testExistsByUsername() {
        boolean exists = userRepository.existsByUsername(user1.getUsername());

        assertEquals(true, exists); // Проверка, что пользователь с данным именем существует
    }
}