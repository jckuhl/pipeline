package com.revature.exceptions;

public class EmployeeException extends Exception {

	@Override
	public String getMessage() {
		return "Employee is already in database!";
	}

	
	
}
