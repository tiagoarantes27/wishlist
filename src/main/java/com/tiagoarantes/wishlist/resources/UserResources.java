package com.tiagoarantes.wishlist.resources;

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

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.domain.User;
import com.tiagoarantes.wishlist.dto.UserDTO;
import com.tiagoarantes.wishlist.dto.VerifyProductResponseDTO;
import com.tiagoarantes.wishlist.services.ProductServices;
import com.tiagoarantes.wishlist.services.UserServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Gerenciar lista de desejos do cliente")
public class UserResources {

	@Autowired
	private UserServices service;
	
	@Autowired
	private ProductServices serviceProduct;

	@Operation(summary = "Buscar todos os clientes cadastrados")
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		var list = service.findAll();
		var listDTO = list.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@Operation(summary = "Buscar o cliente pelo id")
	@GetMapping(value="/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		var user = service.findById(id);
		var userResponse = new UserDTO(user);
		return ResponseEntity.ok().body(userResponse);
	}
	
	@Operation(summary = "Excluir um cliente cadastrado")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Inserir um cliente")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO request) {
		var user = User.builder().email(request.getEmail()).documento(request.getDocumento()).name(request.getName()).id(request.getId()).build();
		service.insert(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Atualizar um cliente cadastrado")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO request, @PathVariable String id) {
		var user = User.builder().email(request.getEmail()).name(request.getName()).id(id).build();
		service.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Consultar todos os produtos da lista de desejos do cliente")
	@GetMapping(value="/{id}/wishlist")
	public ResponseEntity<List<Product>> findWishList(@PathVariable String id) {
		var user = service.findById(id);
		return ResponseEntity.ok().body(user.getWishlist());
	}
	
	@Operation(summary = "Adicionar um produto na lista de desejos do cliente")
	@PostMapping("/{userId}/product/{productId}")
	public ResponseEntity<Void> insertProductWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.findById(userId);
		var product = serviceProduct.findById(productId);
		service.insertProductWishList(user, product);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Remover um produto da lista de desejos do cliente")
	@DeleteMapping("/{userId}/product/{productId}")
	public ResponseEntity<Void> deleteProductWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.findById(userId);
		var product = serviceProduct.findById(productId);
		service.deleteProductWishList(user, product);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Consultar se um determinado produto está presente na lista de desejos do cliente")
	@GetMapping("/{userId}/product/{productId}")
	public ResponseEntity<VerifyProductResponseDTO> verifyPresentWishList(@PathVariable String userId, @PathVariable String productId) {
		var user = service.verifyPresentWishList(userId, productId);
		return ResponseEntity.ok().body(user);
	}

}
