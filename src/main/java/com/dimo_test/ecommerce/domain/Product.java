package com.dimo_test.ecommerce.domain;

import lombok.*;
import java.util.List;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @JsonProperty("product_id")
    private String id;
    @JsonProperty("name")
    private String name;
    @Getter
    @JsonProperty("price")
    private double price;
    @JsonProperty("added_at")
    private String added_at;
    @JsonProperty("labels")
    private List<Labels> labels;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Product) {
          Product product = (Product) o;
          if (this.name.equals(product.name)) {
              return true;
          }
        }
        return false;
    }
}
