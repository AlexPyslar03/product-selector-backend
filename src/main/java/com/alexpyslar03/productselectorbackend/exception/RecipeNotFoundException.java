package com.alexpyslar03.productselectorbackend.exception;

/**
 * Исключение, выбрасываемое в случае, если рецепт не найден.
 * Наследует от RuntimeException для представления ошибки, связанной с отсутствием рецепта.
 */
public class RecipeNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения RecipeNotFoundException.
     *
     * @param message Сообщение об ошибке, которое будет передано конструктору родительского класса.
     */
    public RecipeNotFoundException(String message) {
        super(message);
    }
}