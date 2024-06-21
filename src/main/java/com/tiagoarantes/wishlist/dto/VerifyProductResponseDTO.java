package com.tiagoarantes.wishlist.dto;

import com.tiagoarantes.wishlist.domain.Product;

import lombok.Data;

@Data
public class VerifyProductResponseDTO {

	private Product product;
	private boolean hasProductInWishlist;
	private String message;
	public static final String PRODUCT_NOT_FOUND = "Produto não encontrado na lista";
    public static final String PRODUCT_FOUND = "Produto encontrado";
    
    public VerifyProductResponseDTO() {
        this.hasProductInWishlist = false;
        this.message = PRODUCT_NOT_FOUND;
    }

    public VerifyProductResponseDTO(Product product) {
        this.product = product;
        this.hasProductInWishlist = true;
        this.message = PRODUCT_FOUND;
    }

}
