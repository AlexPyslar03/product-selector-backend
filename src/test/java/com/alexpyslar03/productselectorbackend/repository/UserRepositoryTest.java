package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@SpringJUnitConfig
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = userRepository.save(User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .accessLevel(User.AccessLevel.USER)
                .build());

        user2 = userRepository.save(User.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .accessLevel(User.AccessLevel.USER)
                .build());

        user3 = userRepository.save(User.builder()
                .name("Jim")
                .surname("Beam")
                .email("jim.beam@example.com")
                .password("password123")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.of(1990, 1, 2))
                .accessLevel(User.AccessLevel.USER)
                .build());
    }

    @Test
    public void testFindAllByIdIn() {
        List<User> users = userRepository.findAllByIdIn(Arrays.asList(user1.getId(), user2.getId(), user3.getId()));
        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals(new HashSet<>(Arrays.asList(user1, user2, user3)), new HashSet<>(users));
    }
}