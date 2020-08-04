package com.crm.qa.exceptions;

public class DataParameterNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataParameterNotFoundException(String parameter) {
		super("Parameter, " + parameter + ", is not found in the DataTable. Test will exit");
	}
}
