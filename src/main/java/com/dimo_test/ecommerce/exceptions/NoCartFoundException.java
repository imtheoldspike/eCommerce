package com.dimo_test.ecommerce.exceptions;

public class NoCartFoundException extends Exception {
    public NoCartFoundException() {
        super("Cart could not be found");
    }
}
