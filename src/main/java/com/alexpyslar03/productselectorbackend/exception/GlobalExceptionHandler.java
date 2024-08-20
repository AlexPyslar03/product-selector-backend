package com.alexpyslar03.productselectorbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Глобальный обработчик исключений для обработки ошибок, возникающих в приложении.
 * Используется для централизованного управления ошибками и формирования соответствующих ответов клиенту.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения UserNotFoundException.
     *
     * @param ex Исключение, которое будет обработано.
     * @return Ответ с кодом состояния 404 (Not Found) и сообщением об ошибке.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Обработка исключения ProductNotFoundException.
     *
     * @param ex Исключение, которое будет обработано.
     * @return Ответ с кодом состояния 404 (Not Found) и сообщением об ошибке.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Обработка исключения RecipeNotFoundException.
     *
     * @param ex Исключение, которое будет обработано.
     * @return Ответ с кодом состояния 404 (Not Found) и сообщением об ошибке.
     */
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<String> handleRecipeNotFoundException(RecipeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Обработка исключения BadRequestException.
     *
     * @param ex Исключение, которое будет обработано.
     * @return Ответ с кодом состояния 400 (Bad Request) и сообщением об ошибке.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Обработка общего исключения Exception.
     *
     * @param ex Исключение, которое будет обработано.
     * @return Ответ с кодом состояния 500 (Internal Server Error) и сообщением об ошибке.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла непредвиденная ошибка: " + ex.getMessage());
    }
}