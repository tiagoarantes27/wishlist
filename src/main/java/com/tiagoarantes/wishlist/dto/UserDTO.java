package com.tiagoarantes.wishlist.dto;

import java.util.List;

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	
	private String id;
	private String documento;
	private String name;
	private String email;
	private List<Product> wishlist;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.documento = user.getDocumento();
		this.name = user.getName();
		this.email = user.getEmail();
		this.wishlist = user.getWishlist();
	}

}
