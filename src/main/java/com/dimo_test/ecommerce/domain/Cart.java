package com.dimo_test.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("Carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @JsonProperty("cart_id")
    private String id;
    @JsonProperty("products")
    private List<Product> products;
    @JsonProperty("checked_out")
    private boolean checkedOut;
    @JsonProperty
    private double total;
}
