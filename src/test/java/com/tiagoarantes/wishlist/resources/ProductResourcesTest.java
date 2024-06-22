package com.tiagoarantes.wishlist.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.dto.ProductDTO;
import com.tiagoarantes.wishlist.services.ProductServices;
import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductResourcesTest {

	@InjectMocks
	private ProductResources resource;
	
	@Mock
	private ProductServices service;

	@Test
	@DisplayName("Buscar produto por id")
	void getProcuctById() {
		var id = "1";
		var product = Product.builder().id(id).name("Bicicleta").build();
		when(service.findById(id)).thenReturn(product);
		ResponseEntity<Product> responseEntity = resource.findById(id);
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(product, responseEntity.getBody());
	}

	@Test
	@DisplayName("Produto não encontrado")
	void getProductByIdException() {
		var id = "2";
		when(service.findById(id)).thenThrow(ObjectNotFoundException.class);
		assertThrows(ObjectNotFoundException.class, () -> resource.findById(id));
		verify(service).findById(id);
	}

	@Test
	@DisplayName("Buscar todos os produtos cadastrados")
	void findAll() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).imagem("link").name("Bicicleta").build();
		list.add(product);
		when(service.findAll()).thenReturn(list);
		ResponseEntity<List<Product>> responseEntity = resource.findAll();
		verify(service).findAll();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(list, responseEntity.getBody());
	}
	
	@Test
	@DisplayName("Deletar produto cadastrado")
	void deleteProduct() {
		var id = "1";
		doNothing().when(service).delete(id);
		ResponseEntity<Void> responseEntity = resource.delete(id);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(service).delete(id);
	}
	
	@Test
	@DisplayName("Inserir produto")
	void insert() {
		var id = "1";
		var product = Product.builder().id(id).build();
		var productDTO = ProductDTO.builder().id(id).build();
		when(service.insert(ArgumentMatchers.any())).thenReturn(product);
		ResponseEntity<Void> responseEntity = resource.insert(productDTO);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		verify(service).insert(ArgumentMatchers.any());
	}
	
	@Test
	@DisplayName("Atualizar produto cadastrado")
	void update() {
		var id = "1";
		var product = Product.builder().id(id).name("Bola").build();
		var productDTO = ProductDTO.builder().id(id).build();
		when(service.update(ArgumentMatchers.any())).thenReturn(product);
		ResponseEntity<Void> responseEntity = resource.update(productDTO, id);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		verify(service).update(ArgumentMatchers.any());
	}
}
