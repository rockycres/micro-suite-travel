package com.learn.ordersvc.exception;

/**
 * Class that implements FeignClientException in the API
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public class FeignClientException extends Exception {

	private static final long serialVersionUID = -2586209354700102349L;

	public FeignClientException(){
		super();
	}

	public FeignClientException(String msg){
		super(msg);
	}

	public FeignClientException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
