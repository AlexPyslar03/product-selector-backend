package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое в случае некорректного запроса от клиента.
 * Наследует от RuntimeException и предназначено для обработки ошибок, связанных с неверными данными запроса.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Конструктор исключения BadRequestException.
     *
     * @param message Сообщение об ошибке, которое будет передано конструктору родительского класса.
     */
    public BadRequestException(String message) {
        super(message);
    }
}