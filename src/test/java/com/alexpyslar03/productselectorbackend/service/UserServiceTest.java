package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDTO dto = new UserDTO();
        dto.setName("John");
        dto.setSurname("Doe");
        dto.setEmail("john.doe@example.com");
        // Set other fields as needed

        User user = new User();
        // Set fields on the user object

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(dto);
        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testReadAllUsers() {
        User user = new User();
        List<User> users = Collections.singletonList(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.readAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testReadByIdUserFound() {
        User user = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.readById(1L);
        assertNotNull(result);
    }

    @Test
    public void testReadByIdUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.readById(1L));
        assertEquals("User not found - 1", exception.getMessage());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(user);
        assertNotNull(updatedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserNotFound() {
        User user = new User();
        user.setId(1L);

        when(userRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.update(user));
        assertEquals("User not found - 1", exception.getMessage());
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);

        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.delete(1L));
        assertEquals("User not found - 1", exception.getMessage());
    }
}