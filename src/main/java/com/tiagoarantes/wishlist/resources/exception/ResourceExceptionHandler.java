package com.tiagoarantes.wishlist.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tiagoarantes.wishlist.services.exception.ObjectNotFoundException;
import com.tiagoarantes.wishlist.services.exception.RemoveWishlistException;
import com.tiagoarantes.wishlist.services.exception.WishlistException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	public static final String NOT_FOUND_EXCEPTION_MESSAGE = "Não encontrado";
    public static final String WISHLIST_EXCEPTION_MESSAGE = "Produto não inserido";
    public static final String WISHLIST_NOT_PRESENT_EXCEPTION_MESSAGE = "Produto não removido";
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotFoundException e, HttpServletRequest request){
		var status = HttpStatus.NOT_FOUND;
		var err = StandardError.builder().timestamp(System.currentTimeMillis()).error(NOT_FOUND_EXCEPTION_MESSAGE).status(status.value()).message(e.getMessage()).path(request.getRequestURI()).build();
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(WishlistException.class)
	public ResponseEntity<StandardError> wishlistException(WishlistException e, HttpServletRequest request){
		var status = HttpStatus.BAD_REQUEST;
		var err = StandardError.builder().timestamp(System.currentTimeMillis()).error(WISHLIST_EXCEPTION_MESSAGE).status(status.value()).message(e.getMessage()).path(request.getRequestURI()).build();
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(RemoveWishlistException.class)
	public ResponseEntity<StandardError> removeWishlistException(RemoveWishlistException e, HttpServletRequest request){
		var status = HttpStatus.BAD_REQUEST;
		var err = StandardError.builder().timestamp(System.currentTimeMillis()).error(WISHLIST_NOT_PRESENT_EXCEPTION_MESSAGE).status(status.value()).message(e.getMessage()).path(request.getRequestURI()).build();
		return ResponseEntity.status(status).body(err);
	}

}
