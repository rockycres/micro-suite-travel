package com.learn.ordersvc.exception;

/**
 * Class that implements TransactionNotFoundException in the API
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public class OrderNotFoundException extends Exception {

	private static final long serialVersionUID = -2586209354700102349L;
	
	public OrderNotFoundException(){
		super();
	}
	
	public OrderNotFoundException(String msg){
		super(msg);
	}
	
	public OrderNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
