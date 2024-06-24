package com.tiagoarantes.wishlist.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.tiagoarantes.wishlist.domain.User;
import com.tiagoarantes.wishlist.dto.UserDTO;
import com.tiagoarantes.wishlist.dto.VerifyProductResponseDTO;
import com.tiagoarantes.wishlist.services.ProductServices;
import com.tiagoarantes.wishlist.services.UserServices;

@ExtendWith(MockitoExtension.class)
class UserResourcesTest {

	@InjectMocks
	private UserResources resource;

	@Mock
	private UserServices service;
	
	@Mock
	private ProductServices productService;

	@Test
	@DisplayName("Buscar usuário por id")
	void getUserById() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		var userDTO = new UserDTO(user);
		when(service.findById(id)).thenReturn(user);
		ResponseEntity<UserDTO> responseEntity = resource.findById(id);
		verify(service).findById(id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userDTO, responseEntity.getBody());
	}

	@Test
	@DisplayName("Buscar todos os usuários cadastrados")
	void findAll() {
		List<UserDTO> listDTO = new ArrayList<>();
		List<User> list = new ArrayList<>();
		when(service.findAll()).thenReturn(list);
		ResponseEntity<List<UserDTO>> responseEntity = resource.findAll();
		verify(service).findAll();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(listDTO, responseEntity.getBody());
	}
	
	@Test
	@DisplayName("Deletar usuário cadastrado")
	void deleteUser() {
		var id = "1";
		doNothing().when(service).delete(id);
		ResponseEntity<Void> responseEntity = resource.delete(id);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(service).delete(id);
	}
	
	@Test
	@DisplayName("Inserir usuário")
	void insert() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		var userDTO = new UserDTO(user);
		when(service.insert(ArgumentMatchers.any())).thenReturn(user);
		ResponseEntity<Void> responseEntity = resource.insert(userDTO);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	@DisplayName("Atualizar usuário cadastrado")
	void update() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		var userDTO = new UserDTO(user);
		when(service.update(ArgumentMatchers.any())).thenReturn(user);
		ResponseEntity<Void> responseEntity = resource.update(userDTO, id);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	@DisplayName("Buscar wishlist de um usuário")
	void getWishlistById() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		when(service.findById(id)).thenReturn(user);
		ResponseEntity<List<Product>> responseEntity = resource.findWishList(id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(user.getWishlist(), responseEntity.getBody());
	}
	
	@Test
	@DisplayName("Inserir produto na wishlist")
	void insertWishlist() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		when(service.findById(id)).thenReturn(user);
		when(productService.findById(id)).thenReturn(product);
		when(service.insertProductWishList(user, product)).thenReturn(user);
		ResponseEntity<Void> responseEntity = resource.insertProductWishList(id, id);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	@DisplayName("Verificar se produto está presente na wishlist")
	void verifyPresentWishList() {
		var id = "1";
		var verify = VerifyProductResponseDTO.builder().hasProductInWishlist(true).productId(id).message("Produto encontrado").build();
		when(service.verifyPresentWishList(id, id)).thenReturn(verify);
		ResponseEntity<VerifyProductResponseDTO> responseEntity = resource.verifyPresentWishList(id, id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(verify, responseEntity.getBody());
		verify(service).verifyPresentWishList(id, id);
	}

}
