package com.alexpyslar03.productselectorbackend.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Recipe not found - " + id);
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }
}