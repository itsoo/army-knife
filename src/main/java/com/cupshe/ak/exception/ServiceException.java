package com.cupshe.ak.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceException extends RuntimeException{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6951032092071539132L;

	private String code;
	
	private String message;
	
	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
}
