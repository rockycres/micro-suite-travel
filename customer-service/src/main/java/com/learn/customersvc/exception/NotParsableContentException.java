package com.learn.customersvc.exception;

/**
 * Class that implements NotParsableContentException in the API
 */
public class NotParsableContentException extends Exception{

	private static final long serialVersionUID = 6208890125157318839L;
	
	public NotParsableContentException(String msg){
		super(msg);
	}
	
	public NotParsableContentException(String msg, Throwable cause){
		super(msg, cause);
	}

}
