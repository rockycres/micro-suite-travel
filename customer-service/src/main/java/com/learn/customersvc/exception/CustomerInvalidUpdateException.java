package com.learn.customersvc.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 */
public class CustomerInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public CustomerInvalidUpdateException(){
		super();
	}
	
	public CustomerInvalidUpdateException(String msg){
		super(msg);
	}
	
	public CustomerInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
