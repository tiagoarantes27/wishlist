package com.tiagoarantes.wishlist.resources.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StandardError {
	
	private long timestamp;
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;

}
