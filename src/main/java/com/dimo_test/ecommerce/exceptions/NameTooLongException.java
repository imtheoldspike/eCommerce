package com.dimo_test.ecommerce.exceptions;

public class NameTooLongException extends Exception {
    public NameTooLongException() {
        super("Name exceeds 200 characters limit");
    }
}
