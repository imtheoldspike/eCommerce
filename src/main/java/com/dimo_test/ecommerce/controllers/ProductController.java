package com.dimo_test.ecommerce.controllers;

import java.net.URI;
import java.util.List;
import java.net.URISyntaxException;
import java.util.Optional;
import com.dimo_test.ecommerce.domain.Product;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dimo_test.ecommerce.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dimo_test.ecommerce.exceptions.NameTooLongException;
import com.dimo_test.ecommerce.exceptions.NameAleadyExistsException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.dimo_test.ecommerce.domain.Labels.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     *  Gets a list of all products
     */
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Object> getProducts() {
        List<Product> products = productService.listAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    /**
     *  Gets a specific product by id
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getProduct(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        Product foundProduct = product.orElse(null);
        if (foundProduct != null) {
            return ResponseEntity.ok().body(foundProduct);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *  Deletes a specific product by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteById(id);
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Product deleted successfully");
    }

    /**
     *  Adds a product to all available products
     */
    @PostMapping(value ="/", consumes = "application/json")
    public ResponseEntity<Object> addProduct(@RequestBody JsonNode req) {
        //201 response
        Product newlyCreatedProduct;
        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            newlyCreatedProduct = productService.saveProduct(jsonObjectMapper.treeToValue(req,Product.class));
        } catch (NameAleadyExistsException | NameTooLongException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (newlyCreatedProduct != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newlyCreatedProduct.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(newlyCreatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     *  Setup method to add known products
     */
    @GetMapping(value="/init")
    public ResponseEntity<Object> initProducts() throws NameTooLongException, NameAleadyExistsException {
        Product p1 = new Product();
        p1.setName("Fancy IPA Beer");
        p1.setPrice(5.99);
        p1.setId("123");
        p1.setLabels(List.of(DRINK,FOOD));
        productService.saveProduct(p1);
        Product p2 = new Product();
        p2.setName("Delicious Cake");
        p2.setPrice(10.11);
        p2.setId("124");
        p2.setLabels(List.of(FOOD));
        productService.saveProduct(p2);
        Product p3 = new Product();
        p3.setName("Special Smelly Cheese");
        p3.setPrice(20.99);
        p3.setId("125");
        p3.setLabels(List.of(LIMITED,FOOD));
        productService.saveProduct(p3);
        return ResponseEntity.ok().body("Products created successfully");
    }

}