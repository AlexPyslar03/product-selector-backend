package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnCreatedUser() {
        UserDTO userDTO = new UserDTO(); // заполняем DTO при необходимости
        User user = new User(); // заполняем User при необходимости

        when(userService.create(userDTO)).thenReturn(user);

        ResponseEntity<User> response = userController.create(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void readAll_shouldReturnListOfUsers() {
        User user1 = new User(); // заполняем User при необходимости
        User user2 = new User(); // заполняем User при необходимости
        List<User> users = Arrays.asList(user1, user2);

        when(userService.readAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.readAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void readById_shouldReturnUser() {
        Long id = 1L;
        User user = new User(); // заполняем User при необходимости

        when(userService.readById(id)).thenReturn(user);

        ResponseEntity<User> response = userController.readById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void readByIdIn_shouldReturnListOfUsers() {
        List<Long> ids = Arrays.asList(1L, 2L);
        User user1 = new User(); // заполняем User при необходимости
        User user2 = new User(); // заполняем User при необходимости
        List<User> users = Arrays.asList(user1, user2);

        when(userService.readAllByIdIn(ids)).thenReturn(users);

        ResponseEntity<List<User>> response = userController.readByIdIn(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void update_shouldReturnUpdatedUser() {
        User user = new User(); // заполняем User при необходимости

        when(userService.update(user)).thenReturn(user);

        ResponseEntity<User> response = userController.update(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void delete_shouldReturnNoContent() {
        Long id = 1L;

        doNothing().when(userService).delete(id);

        ResponseEntity<Void> response = userController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(id);
    }
}
