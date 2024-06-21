package com.tiagoarantes.wishlist.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tiagoarantes.wishlist.services.ProductServices;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

	@Autowired
	ProductServices service;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		//List<UserDTO> listDTO = list.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> findById(@PathVariable String id) {
		var product = service.findById(id);
		//return ResponseEntity.ok().body(new Product(product));
		return ResponseEntity.ok().body(product);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Product request) {
		//var user = User.builder().email(request.getEmail()).name(request.getName()).id(request.getId()).build();
		var product = service.insert(request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Product request, @PathVariable String id) {
		//var user = User.builder().email(request.getEmail()).name(request.getName()).id(id).build();
		var product = service.update(request);
		return ResponseEntity.noContent().build();
	}

}
