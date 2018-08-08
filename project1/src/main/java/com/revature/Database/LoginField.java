package com.revature.Database;

public enum LoginField {
	USERNAME ("username"),
	PASSWORD ("password");
	
	private String field;
	
	LoginField(String field) {
		this.field = field;
	}
	
	public String getField() {
		return this.field;
	}
}
