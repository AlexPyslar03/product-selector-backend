package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.exception.UserNotFoundException;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnUser() {
        UserDTO userDTO = new UserDTO("John", "Doe", "john.doe@example.com", "password", null, null, null);
        User user = new User(); // Заполняем User соответствующими данными

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(userDTO);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void readAll_shouldReturnListOfUsers() {
        User user1 = new User(); // Заполняем User соответствующими данными
        User user2 = new User(); // Заполняем User соответствующими данными
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.readAll();

        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void readById_shouldReturnUser() {
        Long id = 1L;
        User user = new User(); // Заполняем User соответствующими данными

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.readById(id);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void readById_shouldThrowExceptionWhenUserNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.readById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void readAllByIdIn_shouldReturnListOfUsers() {
        List<Long> ids = Arrays.asList(1L, 2L);
        User user1 = new User(); // Заполняем User соответствующими данными
        User user2 = new User(); // Заполняем User соответствующими данными
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAllByIdIn(ids)).thenReturn(users);

        List<User> result = userService.readAllByIdIn(ids);

        assertEquals(users, result);
        verify(userRepository, times(1)).findAllByIdIn(ids);
    }

    @Test
    void readAllByIdIn_shouldThrowExceptionWhenNoUsersFound() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(userRepository.findAllByIdIn(ids)).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> userService.readAllByIdIn(ids));
        verify(userRepository, times(1)).findAllByIdIn(ids);
    }

    @Test
    void update_shouldUpdateAndReturnUser() {
        User user = new User(); // Заполняем User соответствующими данными

        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(user);

        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).existsById(user.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_shouldThrowExceptionWhenUserNotFound() {
        User user = new User(); // Заполняем User соответствующими данными

        when(userRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.update(user));
        verify(userRepository, times(1)).existsById(user.getId());
    }

    @Test
    void delete_shouldDeleteUser() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);
        doNothing().when(userRepository).deleteById(id);

        userService.delete(id);

        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_shouldThrowExceptionWhenUserNotFound() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.delete(id));
        verify(userRepository, times(1)).existsById(id);
    }
}