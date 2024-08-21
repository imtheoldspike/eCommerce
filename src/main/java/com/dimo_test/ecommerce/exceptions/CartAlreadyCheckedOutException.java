package com.dimo_test.ecommerce.exceptions;

public class CartAlreadyCheckedOutException extends Exception {
    public CartAlreadyCheckedOutException() {
        super("Cart is already checked out");
    }
}
