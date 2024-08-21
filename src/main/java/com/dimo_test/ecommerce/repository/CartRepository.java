package com.dimo_test.ecommerce.repository;

import com.dimo_test.ecommerce.domain.Cart;
import com.dimo_test.ecommerce.domain.Product;
import com.dimo_test.ecommerce.exceptions.CartAlreadyCheckedOutException;
import com.dimo_test.ecommerce.exceptions.NoCartFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CartRepository {

    private final List<Cart> carts;

    public CartRepository(List<Cart> carts) {
        this.carts = carts;
    }

    public Cart createCart(){
        Cart cart = new Cart();
        //generate random id
        Random random = new Random();
        Integer rand;
        do {
            rand = random.nextInt(1000);
            cart.setId(rand.toString());
        }while (findCart(rand.toString(),false) != null);
        cart.setProducts(new ArrayList<Product>());
        cart.setCheckedOut(false);
        this.carts.add(cart);
        return cart;
    }

    public List<Cart> listAllCartsContents(){
        return this.carts;
    }

    /**
     * For simplicity's sake:
     * This method has some limitations in its current state. For example the endpoint works slightly differently to how it is detailed in the spec doc
     * Instead of using a quantity it just lists each product multiple times.
     *
     * It also works on the premise that each request for updating the cart will only add products which are desired to be in the cart after the update..
     * This makes the process simpler as there does not have to be complex comparisons between the current state of products in the cart and what's being updated.
     * It also does not currently allow for items to be removed from the list.
     *
     * I assume this will be a large part of the discussion in the second stage interview.
     */
    public Cart modifyCartContents(String id, List productsToAdd) throws CartAlreadyCheckedOutException {
        //find cart
        Cart cart = findCart(id,true);
        //check if checked out
        if (cart.isCheckedOut()) {
            throw new CartAlreadyCheckedOutException();
        }
        //TODO: work out a way to do this
        //it would be a good enhancement to check the products actually exist before adding them to the list
        List<Product> existingProductsAndNew = cart.getProducts();
        existingProductsAndNew.addAll(productsToAdd);
        cart.setProducts(existingProductsAndNew);
        return cart;
    }

    public Cart checkoutCart(String id) throws NoCartFoundException {
        Cart cart = findCart(id,false);
        if (cart == null) {
            throw new NoCartFoundException();
        }
        cart.setCheckedOut(true);
        //work out total
        double total = 0.00;
        List<Product> list = cart.getProducts();
        for (int i = 0; i < list.size(); i++) {
            //TODO:fix this as its not currently working
            total = total + list.get(i).getPrice();
        }
        cart.setTotal(total);
        return cart;
    }

    private Cart findCart(String id,boolean createNewAllowed) {
        Cart cart = null;
        Optional<Cart> ret;
        List<Cart> carts = this.carts.stream().filter(c -> Objects.equals(c.getId(), id)).toList();
        if(!carts.isEmpty()){
            ret = Optional.of(carts.get(0));
            cart = ret.get();
        } else {
            //cart not found, create a new one
            if (createNewAllowed) {
                cart = new Cart();
            }
        }
        return cart;
    }
}
