package com.pizz.pizzaFactory.ExceptionHandler;

public class PizzaException extends RuntimeException {
	
	private final String userMessage;  
    

    public PizzaException(String userMessage) {
    	super(userMessage);
        this.userMessage = userMessage; 
        
    }

    public String getUserMessage() {
        return userMessage;
    }

}
