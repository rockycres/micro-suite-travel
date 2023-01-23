package com.learn.ordersvc.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
public class OrderInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public OrderInvalidUpdateException(){
		super();
	}
	
	public OrderInvalidUpdateException(String msg){
		super(msg);
	}
	
	public OrderInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
