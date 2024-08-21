package com.dimo_test.ecommerce.services;

import java.util.*;

import com.dimo_test.ecommerce.domain.Cart;
import com.dimo_test.ecommerce.domain.Product;
import com.dimo_test.ecommerce.exceptions.CartAlreadyCheckedOutException;

import com.dimo_test.ecommerce.exceptions.NoCartFoundException;
import com.dimo_test.ecommerce.repository.CartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {

    @Autowired
    CartRepository cartRepository;

    public Cart createCart() {
        return cartRepository.createCart();
    }

    public List<Cart> listAllCartsContents() {
        return cartRepository.listAllCartsContents();
    }

    public Cart modifyCartContents(String id, List productsToAdd) throws CartAlreadyCheckedOutException, JsonProcessingException {
        return cartRepository.modifyCartContents(id, productsToAdd);
    }

    public Cart checkoutCart(String id) throws NoCartFoundException {
        return cartRepository.checkoutCart(id);
    }
}
