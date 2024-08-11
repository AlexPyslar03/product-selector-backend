package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void whenCreateUser_thenReturnUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        User user = User.builder().name("John").build();

        when(userService.create(userDTO)).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void whenReadAll_thenReturnListOfUsers() throws Exception {
        User user1 = User.builder().name("Alice").build();
        User user2 = User.builder().name("Bob").build();

        when(userService.readAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    public void whenReadById_thenReturnUser() throws Exception {
        User user = User.builder().name("Alice").build();
        Long userId = 1L;

        when(userService.readById(userId)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser() throws Exception {
        User user = User.builder().name("Alice").build();

        when(userService.update(user)).thenReturn(user);

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alice\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void whenDeleteUser_thenStatusOk() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/user/{id}", userId))
                .andExpect(status().isOk());
    }
}