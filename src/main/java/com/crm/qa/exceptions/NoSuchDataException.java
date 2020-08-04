package com.crm.qa.exceptions;

public class NoSuchDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoSuchDataException(String parameter) {
		super("Parameter \"" + parameter
				+ "\" is not found in the data sheet.\nPlease check whether the data sheet contains the parameter.");
	}

}
