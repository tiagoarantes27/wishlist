package com.tiagoarantes.wishlist.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imagem;
    
}
