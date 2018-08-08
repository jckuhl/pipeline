package com.revature.exceptions;

public class LoginException extends Exception {

	private String message;
	
	public LoginException() {
		this.message = "Employee already has a username and password!";
	}
	
	public LoginException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	
}
