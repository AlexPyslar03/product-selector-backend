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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDTO dto = new UserDTO();
        User user = new User();

        when(userService.create(any(UserDTO.class))).thenReturn(user);

        ResponseEntity<User> response = userController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testReadAllUsers() {
        User user = new User();
        List<User> users = Collections.singletonList(user);

        when(userService.readAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.readAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testReadByIdUserFound() {
        User user = new User();

        when(userService.readById(anyLong())).thenReturn(user);

        ResponseEntity<User> response = userController.readById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testReadByIdUserNotFound() {
        when(userService.readById(anyLong())).thenThrow(new RuntimeException("User not found - 1"));

        ResponseEntity<User> response = userController.readById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();

        when(userService.update(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.update(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userService).delete(anyLong());

        ResponseEntity<Void> response = userController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}