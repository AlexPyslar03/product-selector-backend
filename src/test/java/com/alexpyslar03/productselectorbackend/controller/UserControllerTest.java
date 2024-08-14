package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        User user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .build();

        when(userService.create(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testReadAllUsers() throws Exception {
        User user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .build();

        when(userService.readAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    // Другие тесты, например, на получение по ID, обновление, удаление
}
