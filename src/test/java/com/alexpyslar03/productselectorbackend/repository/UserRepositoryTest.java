package com.alexpyslar03.productselectorbackend.repository;

import com.alexpyslar03.productselectorbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAllByIdIn_ShouldReturnCorrectUsers() {
        User user1 = new User();
        user1.setName("User1");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("User2");
        userRepository.save(user2);

        User user3 = new User();
        user3.setName("User3");
        user3 = userRepository.save(user3);

        List<Long> ids = Arrays.asList(user1.getId(), user3.getId());
        List<User> users = userRepository.findAllByIdIn(ids);

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getId).containsExactlyInAnyOrder(user1.getId(), user3.getId());
    }

    @Test
    public void testFindAllByIdIn_ShouldReturnEmptyList_WhenNoUsersFound() {
        List<Long> ids = Arrays.asList(999L, 1000L);
        List<User> users = userRepository.findAllByIdIn(ids);

        assertThat(users).isEmpty();
    }

    @Test
    public void testFindAllByIdIn_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<User> users = userRepository.findAllByIdIn(List.of());

        assertThat(users).isEmpty();
    }
}