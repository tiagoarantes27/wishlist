package com.tiagoarantes.wishlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tiagoarantes.wishlist.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	@Query("{ '_id': ?0, 'wishlist': { $elemMatch: { 'id': ?1 } } }")
	User isPresent(String userId, String productId);

}
