package com.tiagoarantes.wishlist.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.domain.User;
import com.tiagoarantes.wishlist.dto.VerifyProductResponseDTO;
import com.tiagoarantes.wishlist.repository.UserRepository;
import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;
import com.tiagoarantes.wishlist.services.exception.RemoveWishlistException;
import com.tiagoarantes.wishlist.services.exception.WishlistException;

@Service
public class UserServices {

	@Autowired
	private UserRepository repo;
	
	public static final String NOT_FOUND_EXCEPTION_MESSAGE = "Cliente não encontrado";
    public static final String WISHLIST_EXCEPTION_MESSAGE = "Lista cheia";
    public static final String WISHLIST_PRESENT_EXCEPTION_MESSAGE = "Produto já está presente na lista de desejos";
    public static final String WISHLIST_EMPTY_EXCEPTION_MESSAGE = "Lista de desejos já está vazia";
    public static final String WISHLIST_OVER_EXCEPTION_MESSAGE = "Produto não está presente na lista de desejos";
    public static final int WISHLIST_MAX_SIZE = 20;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_EXCEPTION_MESSAGE));
	}

	public User insert(User user) {
		return repo.insert(user);
	}
	
	public User update(User obj) {
		var user = findById(obj.getId());
		updateData(user, obj);
		return repo.save(user);
	}
	
	private void updateData(User user, User obj) {
		user.setEmail(obj.getEmail());
		user.setName(obj.getName());
		user.setDocumento(obj.getDocumento());
		user.setWishlist(obj.getWishlist());
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User insertProductWishList(User user, Product product) {
		if(Objects.isNull(user.getWishlist())) {
			List<Product> wishlist = new ArrayList<>(); 
			user.setWishlist(wishlist);
			user.getWishlist().add(product);
			return repo.save(user);
		}
		if(user.getWishlist().size() >= WISHLIST_MAX_SIZE) {
			throw new WishlistException(WISHLIST_EXCEPTION_MESSAGE);
		}
		var obj = verifyPresentWishList(user.getId(), product.getId());
		if(obj.isHasProductInWishlist()) {
			throw new WishlistException(WISHLIST_PRESENT_EXCEPTION_MESSAGE);
		}
		user.getWishlist().add(product);
		return repo.save(user);
	}

	public VerifyProductResponseDTO verifyPresentWishList(String userId, String productId) {
		var user = repo.isPresent(userId, productId);
		if(Objects.isNull(user)) {
			return new VerifyProductResponseDTO();
        }
		return new VerifyProductResponseDTO(user.getWishlist().get(0));
	}

	public void deleteProductWishList(User user, Product product) {
		if(Objects.isNull(user.getWishlist())) {
			throw new RemoveWishlistException(WISHLIST_EMPTY_EXCEPTION_MESSAGE);
		}

		if(!user.getWishlist().remove(product)){
			throw new RemoveWishlistException(WISHLIST_OVER_EXCEPTION_MESSAGE);
		}
		repo.save(user);
	}

}
