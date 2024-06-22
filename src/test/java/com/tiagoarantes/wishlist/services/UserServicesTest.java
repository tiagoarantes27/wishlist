package com.tiagoarantes.wishlist.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.tiagoarantes.wishlist.domain.User;
import com.tiagoarantes.wishlist.dto.VerifyProductResponseDTO;
import com.tiagoarantes.wishlist.repository.UserRepository;
import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;
import com.tiagoarantes.wishlist.services.exception.WishlistException;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {
	
	@InjectMocks
	private UserServices service;
	
	@Mock
	private UserRepository repo;
	
	@Test
	@DisplayName("Buscar usuário por id")
	void getUserById() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		when(repo.findById(id)).thenReturn(Optional.of(user));
		var result = service.findById(id);
		assertEquals(user, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
	}
	
	@Test
	@DisplayName("Usuário não encontrado")
	void getUserByIdException() {
		var id = "2";
		when(repo.findById(id)).thenThrow(ObjectNotFoundException.class);
		assertThrows(ObjectNotFoundException.class, () -> service.findById(id));
	}
	
	@Test
	@DisplayName("Buscar todos os usuários cadastrados")
	void findAll() {
		var id = "1";
		List<User> listUser = new ArrayList<>();
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		listUser.add(user);
		when(repo.findAll()).thenReturn(listUser);
		var result = service.findAll();
		assertEquals(listUser, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(listUser);
	}
	
	@Test
	@DisplayName("Atualizar usuário cadastrado")
	void update() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("12").name("teste").email("teste@gmail.com").wishlist(list).build();
		when(repo.save(user)).thenReturn(user);
		when(repo.findById(id)).thenReturn(Optional.of(user));
		var result = service.update(user);
		assertEquals(user, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
	}
	
	@Test
	@DisplayName("Deletar usuário cadastrado")
	void deleteUser() {
		var id = "3";
		doNothing().when(repo).deleteById(id);
		var user = User.builder().id(id).build();
		when(repo.findById(id)).thenReturn(Optional.of(user));
		service.delete(id);
        verify(repo).deleteById(id);
	}
	
	@Test
	@DisplayName("Inserir usuário")
	void insert() {
		var id = "1";
		var user = User.builder().id(id).documento("123").build();
		when(repo.insert(user)).thenReturn(user);
		var result = service.insert(user);
		assertEquals(user, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
	}
	
	@Test
	@DisplayName("Inserir produto na wishlist vazia")
	void insertWishlistEmpty() {
		var id = "1";
		var user = User.builder().id(id).documento("123").build();
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var userResponse = User.builder().id(id).documento("123").wishlist(list).build();
		when(repo.save(user)).thenReturn(user);
		var result = service.insertProductWishList(user, product);
		assertEquals(userResponse, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);
	}
	
	@Test
	@DisplayName("Inserir produto na wishlist")
	void insertWishlist() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var user = User.builder().id(id).documento("123").wishlist(list).build();
		List<Product> listResponse = new ArrayList<>();
		var product = Product.builder().id(id).build();
		listResponse.add(product);
		var userResponse = User.builder().id(id).documento("123").wishlist(listResponse).build();
		when(repo.isPresent(id, id)).thenReturn(null);
		when(repo.save(user)).thenReturn(user);
		var result = service.insertProductWishList(user, product);
		assertEquals(userResponse, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(userResponse);
	}
	
	@Test
	@DisplayName("Inserir produto já existente na lista")
	void insertWishlistIsPresent() {
		var id = "2";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("123").wishlist(list).build();
		when(repo.isPresent(id, id)).thenReturn(user);
		assertThrows(WishlistException.class, () -> service.insertProductWishList(user, product));
	}
	
	@Test
	@DisplayName("Inserir produto com lista cheia")
	void insertWishlistFull() {
		var id = "1";
		var product = Product.builder().id(id).build();
		List<Product> list = listFull();
		var user = User.builder().id(id).wishlist(list).build();
		assertThrows(WishlistException.class, () -> service.insertProductWishList(user, product));
	}
	
	@Test
	@DisplayName("Verificar se produto está presente na lista True")
	void verifyWishlistTrue() {
		var id = "1";
		List<Product> list = new ArrayList<>();
		var product = Product.builder().id(id).build();
		list.add(product);
		var user = User.builder().id(id).documento("123").wishlist(list).build();
		when(repo.isPresent(id, id)).thenReturn(user);
		var sucesso = new VerifyProductResponseDTO(product);
		var result = service.verifyPresentWishList(id, id);
		assertEquals(sucesso, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(sucesso);
	}
	
	@Test
	@DisplayName("Verificar se produto está presente na lista False")
	void verifyWishlistFalse() {
		var id = "1";
		when(repo.isPresent(id, id)).thenReturn(null);
		var sucesso = new VerifyProductResponseDTO();
		var result = service.verifyPresentWishList(id, id);
		assertEquals(sucesso, result);
        assertThat(result).usingRecursiveComparison().isEqualTo(sucesso);
	}

	private List<Product> listFull() {
		List<Product> list = new ArrayList<>();
		Integer i;
		for(i=1;i<=20;i++) {
			var product = Product.builder().id(i.toString()).build(); 
			list.add(product);
		}
		return list;
	}

}
