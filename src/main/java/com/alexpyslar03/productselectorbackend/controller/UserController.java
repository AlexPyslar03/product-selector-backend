package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO dto) {
        return mappingResponseUser(userService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        return mappingResponseListUser(userService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readById(@PathVariable Long id) {
        return mappingResponseUser(userService.readById(id));
    }

    @GetMapping("/{ids}")
    public ResponseEntity<List<User>> readByIds(@PathVariable List<Long> ids) {
        return mappingResponseListUser(userService.readByIds(ids));
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return mappingResponseUser(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<User> mappingResponseUser(User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private ResponseEntity<List<User>> mappingResponseListUser(List<User> users) {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
