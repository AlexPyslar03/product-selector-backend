package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое при некорректных данных, переданных в запросе.
 * Наследуется от RuntimeException, что позволяет использовать его как необязательное для обработки.
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке.
     *
     * @param message Сообщение, описывающее причину возникновения исключения.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}