package com.dimo_test.ecommerce.repository;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import com.dimo_test.ecommerce.domain.Product;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dimo_test.ecommerce.exceptions.NameTooLongException;
import com.dimo_test.ecommerce.exceptions.NameAleadyExistsException;

@Repository
public class ProductRepository {

    private final List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = products;
    }


    public List<Product> findAll(){
        return this.products;
    }

    public Optional<Product> findById(String id){
        Optional<Product> ret = Optional.empty();
        List<Product> filteredProducts = this.products.stream().filter(p -> Objects.equals(p.getId(), id)).toList();
        if(!filteredProducts.isEmpty()){
            ret = Optional.of(filteredProducts.get(0));
        }
        return ret;
    }

    public void deleteById(String id) {
        this.products.removeIf(p -> Objects.equals(p.getId(), id));
    }

    public Product saveProduct(Product product) throws NameAleadyExistsException, NameTooLongException {
        //check if product exists
        ObjectMapper objectMapper = new ObjectMapper();
        Product p = objectMapper.convertValue(product, Product.class);
        List<Product> existingProducts = findAll();
        if(existingProducts.contains(p)) {
            throw new NameAleadyExistsException();
        }
        if (p.getName().length()>200) {
            throw new NameTooLongException();
        }
        //generate random id
        Random random = new Random();
        Integer rand;
        do {
            rand = random.nextInt(1000);
            p.setId(rand.toString());
        }while (findById(rand.toString()).isPresent());
        //save if not exist
        LocalDateTime ldt = LocalDateTime.now();
        String x = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt);
        Product newProduct = new Product(p.getId(), p.getName(), p.getPrice(), x, p.getLabels());
        this.products.add(newProduct);

        return newProduct;
    }
}

