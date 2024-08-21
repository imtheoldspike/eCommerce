package com.dimo_test.ecommerce.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.dimo_test.ecommerce.domain.Cart;
import com.dimo_test.ecommerce.domain.Product;
import com.dimo_test.ecommerce.exceptions.ProductDoesNotExistException;
import com.dimo_test.ecommerce.repository.ProductRepository;
import com.dimo_test.ecommerce.services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dimo_test.ecommerce.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dimo_test.ecommerce.exceptions.NoCartFoundException;
import com.dimo_test.ecommerce.exceptions.CartAlreadyCheckedOutException;



@RestController
@RequestMapping("/carts")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
    }

    /**
     *  Adds an empty cart
     */
    @PostMapping(value ="/")
    public ResponseEntity<Object> createCart() {
        Cart createdCart = cartService.createCart();
        return ResponseEntity.ok().body(createdCart);
    }

    /**
     *  Lists all products currently in the cart
     */
    @GetMapping(value ="/")
    public ResponseEntity<Object> listAllCartsContents() {
        List<Cart> allCarts = cartService.listAllCartsContents();
        return ResponseEntity.ok().body(allCarts);
    }

    /**
     *  Modify contents of cart by id
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyCartContents(@PathVariable String id,@RequestBody JsonNode productList) throws JsonProcessingException {
        Cart updatedCart;
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Product> productList1 = mapper.treeToValue(productList,List.class);
            updatedCart = cartService.modifyCartContents(id, productList1);
        } catch (CartAlreadyCheckedOutException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(updatedCart);
    }

    /**
     *  Checkout with cart contents
     */
    @PostMapping(value ="/{id}/checkout")
    public ResponseEntity<Object> checkoutCart(@PathVariable String id) {
        Cart cart;
        try {
            cart = cartService.checkoutCart(id);
        } catch (NoCartFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(cart);
    }
}