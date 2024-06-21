package com.tiagoarantes.wishlist.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.domain.User;
import com.tiagoarantes.wishlist.dto.UserDTO;
import com.tiagoarantes.wishlist.dto.VerifyProductResponseDTO;
import com.tiagoarantes.wishlist.services.ProductServices;
import com.tiagoarantes.wishlist.services.UserServices;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

	@Autowired
	UserServices service;
	
	@Autowired
	ProductServices serviceProduct;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDTO = list.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		var user = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(user));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO request) {
		var user = User.builder().email(request.getEmail()).documento(request.getDocumento()).name(request.getName()).id(request.getId()).build();
		user = service.insert(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO request, @PathVariable String id) {
		var user = User.builder().email(request.getEmail()).name(request.getName()).id(id).build();
		user = service.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value="/{id}/wishlist")
	public ResponseEntity<List<Product>> findWishList(@PathVariable String id) {
		var user = service.findById(id);
		return ResponseEntity.ok().body(user.getWishlist());
	}
	
	@PostMapping("/{userId}/product/{productId}")
	public ResponseEntity<Void> insertProductWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.findById(userId);
		var product = serviceProduct.findById(productId);
		user = service.insertProductWishList(user, product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{userId}/product/{productId}")
	public ResponseEntity<Void> deleteProductWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.findById(userId);
		var product = serviceProduct.findById(productId);
		service.deleteProductWishList(user, product);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{userId}/product/{productId}")
	public ResponseEntity<VerifyProductResponseDTO> verifyPresentWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.verifyPresentWishList(userId, productId);
		return ResponseEntity.ok().body(user);
	}

}
