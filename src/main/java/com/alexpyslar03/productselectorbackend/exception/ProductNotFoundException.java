package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое в случае, если продукт не найден.
 * Наследует от RuntimeException и предназначено для обработки ситуаций, когда продукт отсутствует в системе.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения ProductNotFoundException.
     *
     * @param message Сообщение об ошибке, которое будет передано конструктору родительского класса.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}