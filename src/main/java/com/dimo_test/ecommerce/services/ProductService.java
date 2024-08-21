package com.dimo_test.ecommerce.services;

import java.util.List;
import java.util.Optional;
import com.dimo_test.ecommerce.domain.Product;
import org.springframework.stereotype.Service;
import com.dimo_test.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.dimo_test.ecommerce.exceptions.NameTooLongException;
import com.dimo_test.ecommerce.exceptions.NameAleadyExistsException;


@Service
public class ProductService implements IProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) throws NameTooLongException, NameAleadyExistsException {
        return productRepository.saveProduct(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct;
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
