package com.pizz.pizzaFactory.DTO;

public class ResponseDTO {
	
	private Object data;
	private boolean error;
	private String message;
	public ResponseDTO(Object data, boolean error, String message) {
		super();
		this.data = data;
		this.error = error;
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


    
}
