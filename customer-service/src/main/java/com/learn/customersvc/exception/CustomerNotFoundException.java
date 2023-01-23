package com.learn.customersvc.exception;

/**
 * Class that implements TransactionNotFoundException in the API
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = -2586209354700102349L;
	
	public CustomerNotFoundException(){
		super();
	}
	
	public CustomerNotFoundException(String msg){
		super(msg);
	}
	
	public CustomerNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
