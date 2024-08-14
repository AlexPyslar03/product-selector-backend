package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .build();

        userDTO = UserDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(userDTO);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("John");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testReadById_UserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.readById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("John");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testReadById_UserDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.readById(1L));

        assertThat(exception.getMessage()).isEqualTo("User not found - 1");
        verify(userRepository, times(1)).findById(1L);
    }

    // Другие тесты, например, на обновление и удаление
}
