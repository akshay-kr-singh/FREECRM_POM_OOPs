package com.crm.qa.exceptions;

public class SheetNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SheetNotFoundException() {
		super("The Data Sheet provided is empty or does not contain the sheet name provided");
	}

}
