package com.tiagoarantes.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.repository.ProductRepository;
import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServicesTest {
	
	@InjectMocks
	private ProductServices service;
	
	@Mock
	private ProductRepository repo;
	
	@Test
	@DisplayName("Buscar produto por id")
	void getProductById() {
		var id = "1";
		var product = Product.builder().id(id).name("Bicicleta").price(BigDecimal.ONE).description("description").imagem("link").build();
		when(repo.findById(id)).thenReturn(Optional.of(product));
		var result = service.findById(id);
		assertEquals(product, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}
	
	@Test
	@DisplayName("Produto não encontrado")
	void getProductByIdException() {
		var id = "2";
		when(repo.findById(id)).thenThrow(ObjectNotFoundException.class);
		assertThrows(ObjectNotFoundException.class, () -> service.findById(id));
	}
	
	@Test
	@DisplayName("Buscar todos os produtos cadastrados")
	void findAll() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		when(repo.findAll()).thenReturn(list);
		var result = service.findAll();
		assertEquals(list, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(list);
	}
	
	@Test
	@DisplayName("Atualizar produto cadastrado")
	void update() {
		var id = "1";
		var product = Product.builder().id(id).build();
		when(repo.save(product)).thenReturn(product);
		when(repo.findById(id)).thenReturn(Optional.of(product));
		var result = service.update(product);
		assertEquals(product, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}
	
	@Test
	@DisplayName("Deletar produto cadastrado")
	void deleteProduct() {
		var id = "3";
		doNothing().when(repo).deleteById(id);
		var product = Product.builder().id(id).build();
		when(repo.findById(id)).thenReturn(Optional.of(product));
		service.delete(id);
        verify(repo).deleteById(id);
	}
	
	@Test
	@DisplayName("Inserir produto")
	void insert() {
		var id = "1";
		var product = Product.builder().id(id).build();
		when(repo.insert(product)).thenReturn(product);
		var result = service.insert(product);
		assertEquals(product, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}
	
}
