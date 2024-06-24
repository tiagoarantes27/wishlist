package com.tiagoarantes.wishlist.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tiagoarantes.wishlist.services.constants.WishlistConstants.*;
import com.tiagoarantes.wishlist.domain.Product;
import com.tiagoarantes.wishlist.repository.ProductRepository;
import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;

@Service
public class ProductServices {

	
	private ProductRepository repo;

	@Autowired
	public ProductServices(ProductRepository repository) {
		this.repo = repository;
	}
	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product findById(String id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_EXCEPTION_MESSAGE));
	}

	public Product insert(Product product) {
		return repo.insert(product);
	}
	
	public Product update(Product obj) {
		var product = findById(obj.getId());
		updateData(product, obj);
		return repo.save(product);
	}
	
	private void updateData(Product product, Product obj) {
		product.setPrice(obj.getPrice());
		product.setName(obj.getName());
		product.setDescription(obj.getDescription());
		product.setImagem(obj.getImagem());
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

}
