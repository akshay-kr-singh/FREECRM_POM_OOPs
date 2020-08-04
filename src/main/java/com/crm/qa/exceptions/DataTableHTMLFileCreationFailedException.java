package com.crm.qa.exceptions;

public class DataTableHTMLFileCreationFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataTableHTMLFileCreationFailedException(Exception e) {
		super("DataFile HTML file creation for reporting failed with the following error:/n" + e.getMessage());
	}
}
