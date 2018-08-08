package com.revature.Database;

/**
 * Enumerated values for columns in database
 * @author jonathankuhl
 *
 */
public enum EmpField {
	FNAME ("fname"),
	LNAME ("lname"),
	POSITION ("position"),
	STREET ("street"),
	CITY ("city"),
	STATE ("state"),
	ZIP ("zip"),
	PHONE ("phone"),
	EMAIL ("email");
	
	private String field;
	
	EmpField(String field) {
		this.field = field;
	}
	
	public String getField() {
		return this.field;
	}
}
