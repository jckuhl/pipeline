package com.revature.model;

public enum Position {
	MANAGER ("Regional Manager"),
	ASSISTANT ("Assistant To The Regional Manager"),
	SALES ("Sales"),
	RECEPTION ("Reception"),
	ACCOUNTING ("Accounting"),
	HR ("Human Resources"),
	QA ("Quality Assurance"),
	CS ("Customer Service");
	
	private String position;
	
	Position(String position) {
		this.position = position;
	}
	
	/**
	 * Allows me to get a string value to send to DB
	 * @return string value of enum
	 */
	public String getPosition() {
		return this.position;
	}
	
	/**
	 * Allows me to fetch Enum value to retrieve from DB
	 * @param string from the DB
	 * @return enum value
	 */
	public static Position fromString(String string) {
		for(Position pos: Position.values()) {
			if(pos.getPosition().equalsIgnoreCase(string)) {
				return pos;
			}
		}
		return null;
	}
}
