package com.tiagoarantes.wishlist.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imagem;
    
}
