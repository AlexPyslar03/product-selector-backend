package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Используйте встроенную базу данных
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");

        user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void findAllByIdIn_shouldReturnListOfUsers() {
        List<Long> ids = Arrays.asList(user1.getId(), user2.getId());
        List<User> users = userRepository.findAllByIdIn(ids);

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void findAllByIdIn_withEmptyList_shouldReturnEmptyList() {
        List<User> users = userRepository.findAllByIdIn(Arrays.asList(999L)); // несуществующий ID

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }
}