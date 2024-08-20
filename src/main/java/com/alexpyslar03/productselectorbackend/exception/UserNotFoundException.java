package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое в случае, если пользователь не найден.
 * Наследует от RuntimeException для представления ошибки, связанной с отсутствием пользователя.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения UserNotFoundException.
     *
     * @param message Сообщение об ошибке, которое будет передано конструктору родительского класса.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}