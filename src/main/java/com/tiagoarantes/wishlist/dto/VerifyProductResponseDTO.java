package com.tiagoarantes.wishlist.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyProductResponseDTO {

	private String productId;
	private boolean hasProductInWishlist;
	private String message;

}
