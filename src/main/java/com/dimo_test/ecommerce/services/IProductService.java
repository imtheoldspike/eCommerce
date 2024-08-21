package com.dimo_test.ecommerce.services;

import com.dimo_test.ecommerce.domain.Product;
import com.dimo_test.ecommerce.exceptions.NameAleadyExistsException;
import com.dimo_test.ecommerce.exceptions.NameTooLongException;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    public List<Product> listAllProducts();

    public Product saveProduct(Product product) throws NameTooLongException, NameAleadyExistsException;

    public Optional<Product> getProductById(String id);

    public void deleteById(String id);
}
