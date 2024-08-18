package com.alexpyslar03.productselectorbackend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found - " + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
