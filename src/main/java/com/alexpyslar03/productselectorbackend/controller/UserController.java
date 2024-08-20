package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.exception.UserNotFoundException;
import com.alexpyslar03.productselectorbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с пользователями.
 * Предоставляет endpoint'ы для создания, чтения, обновления и удаления пользователей.
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Создает нового пользователя.
     *
     * @param dto DTO с данными нового пользователя.
     * @return Ответ с созданным пользователем и статусом 201 Created.
     */
    @Operation(summary = "Создание нового пользователя", description = "Создает нового пользователя и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для создания пользователя")
    })
    @PostMapping
    public ResponseEntity<User> create(
            @Parameter(description = "DTO с данными нового пользователя", required = true) @RequestBody UserDTO dto) {
        User createdUser = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Ответ со списком всех пользователей и статусом 200 OK.
     */
    @Operation(summary = "Получение списка всех пользователей", description = "Возвращает список всех пользователей в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно возвращен")
    })
    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        List<User> users = userService.readAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Ответ с пользователем и статусом 200 OK.
     * @throws UserNotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @Operation(summary = "Получение пользователя по ID", description = "Возвращает пользователя по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> readById(
            @Parameter(description = "Идентификатор пользователя", required = true) @PathVariable Long id) {
        User user = userService.readById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Возвращает список пользователей по предоставленным идентификаторам.
     *
     * @param ids Список идентификаторов пользователей.
     * @return Ответ со списком пользователей и статусом 200 OK.
     * @throws UserNotFoundException Если ни один из пользователей с указанными идентификаторами не найден.
     */
    @Operation(summary = "Получение списка пользователей по ID", description = "Возвращает список пользователей по указанным ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно возвращен"),
            @ApiResponse(responseCode = "404", description = "Не найдены пользователи с указанными ID")
    })
    @GetMapping("/batch")
    public ResponseEntity<List<User>> readByIdIn(
            @Parameter(description = "Список идентификаторов пользователей", required = true) @RequestParam List<Long> ids) {
        List<User> users = userService.readAllByIdIn(ids);
        return ResponseEntity.ok(users);
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param user Пользователь с обновленными данными.
     * @return Ответ с обновленным пользователем и статусом 200 OK.
     * @throws UserNotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @Operation(summary = "Обновление данных пользователя", description = "Обновляет данные пользователя и возвращает его.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден")
    })
    @PutMapping
    public ResponseEntity<User> update(
            @Parameter(description = "Пользователь с обновленными данными", required = true) @RequestBody User user) {
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя для удаления.
     * @return Ответ со статусом 204 No Content.
     * @throws UserNotFoundException Если пользователь с указанным идентификатором не найден.
     */
    @Operation(summary = "Удаление пользователя по ID", description = "Удаляет пользователя по указанному ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным ID не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор пользователя для удаления", required = true) @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}