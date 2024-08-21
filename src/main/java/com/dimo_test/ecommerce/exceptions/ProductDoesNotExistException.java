package com.dimo_test.ecommerce.exceptions;

public class ProductDoesNotExistException extends Exception {
    public ProductDoesNotExistException() {
        super("Product does not exist");
    }
}
