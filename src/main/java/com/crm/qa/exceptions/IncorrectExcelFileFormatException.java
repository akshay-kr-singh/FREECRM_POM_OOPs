package com.crm.qa.exceptions;

public class IncorrectExcelFileFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IncorrectExcelFileFormatException(String dataFileLocation) {
		super("The Data file provided is in the wrong format:\n" + dataFileLocation
				+ "\nPlease provide a xls/xlsx/xlsm file. Test will exit.");
	}
}
