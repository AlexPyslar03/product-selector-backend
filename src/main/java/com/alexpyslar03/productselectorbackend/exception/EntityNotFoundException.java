package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое в случае, если сущность не найдена в базе данных.
 * Наследуется от RuntimeException, что позволяет использовать его как необязательное для обработки.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке.
     *
     * @param message Сообщение, описывающее причину возникновения исключения.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
