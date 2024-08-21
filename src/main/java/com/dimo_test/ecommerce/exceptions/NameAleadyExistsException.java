package com.dimo_test.ecommerce.exceptions;

public class NameAleadyExistsException extends Exception {
    public NameAleadyExistsException() {
        super("Name already exists");
    }
}
