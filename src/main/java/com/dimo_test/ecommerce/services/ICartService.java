package com.dimo_test.ecommerce.services;

import com.dimo_test.ecommerce.domain.Cart;
import com.dimo_test.ecommerce.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dimo_test.ecommerce.exceptions.NoCartFoundException;
import com.dimo_test.ecommerce.exceptions.CartAlreadyCheckedOutException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public interface ICartService {

    public Cart createCart();

    public List<Cart> listAllCartsContents();

    public Cart modifyCartContents(String id, List productsToAdd) throws CartAlreadyCheckedOutException, JsonProcessingException;

    public Cart checkoutCart(String id) throws NoCartFoundException;
}
