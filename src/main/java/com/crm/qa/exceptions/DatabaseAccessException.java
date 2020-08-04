package com.crm.qa.exceptions;

public class DatabaseAccessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DatabaseAccessException(Exception e) {
		super("Database Access Error : ", e);
	}

	public DatabaseAccessException(String error) {
		super(error);
	}

}
