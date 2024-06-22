package com.tiagoarantes.wishlist.resources;

import java.util.List;

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
import com.tiagoarantes.wishlist.dto.ProductDTO;
import com.tiagoarantes.wishlist.services.ProductServices;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

	@Autowired
	private ProductServices service;

	@Operation(summary = "Buscar todos os produtos cadastrados")
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		var list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@Operation(summary = "Buscar o produto pelo id")
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> findById(@PathVariable String id) {
		var product = service.findById(id);
		return ResponseEntity.ok().body(product);
	}
	
	@Operation(summary = "Excluir um produto")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Inserir um produto")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody ProductDTO request) {
		var product = Product.builder().description(request.getDescription()).imagem(request.getImagem()).name(request.getName()).price(request.getPrice()).build();
		service.insert(product);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Atualizar um produto")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody ProductDTO request, @PathVariable String id) {
		var product = Product.builder().description(request.getDescription()).imagem(request.getImagem()).name(request.getName()).price(request.getPrice()).build();
		service.update(product);
		return ResponseEntity.noContent().build();
	}

}
