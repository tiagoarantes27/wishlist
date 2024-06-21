package com.tiagoarantes.wishlist.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="user")
public class User {
	
	@Id
	private String id;
	private String documento;
	private String name;
	private String email;
	private List<Product> wishlist;
}
