package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.crm.qa.exceptions.DataFileNotFoundException;
import com.crm.qa.exceptions.IncorrectExcelFileFormatException;
import com.crm.qa.exceptions.SheetNotFoundException;

/**
 * This class contains methods to read data files[excel/XML] and provide data to
 * the test in the form of {@link DataTable} instances.
 * 
 * @see DataTable
 * @author sighil.sivadas
 * 
 */
public class DataFetcher {
	private static final Logger LOGGER = LogManager.getLogger(DataFetcher.class);

	private ArrayList<LinkedHashMap<String, String>> iterationDataSet;

	/**
	 * This method converts the iterationDataSet, which is an ArrayList containing
	 * the data sets for each iteration saved as a <key, value> pair, to an 2
	 * dimensional Object[][] array containing DataTable instances. Each DataTable
	 * instance corresponds to one iteration data set.
	 * 
	 * @return Object[n][1] containing (n-1) instances of DataTable instances
	 */

	private Object[][] generateData() {
		Object[][] object = new Object[iterationDataSet.size()][1];
		int i = 0;
		for (i = 0; i < iterationDataSet.size(); i++) {
			DataTable dataTable = new DataTable(iterationDataSet.get(i));
			object[i][0] = dataTable;
		}
		LOGGER.info(i + " rows of data processed and converted to DataTable");
		return object;
	}

	/**
	 * This method read the contents in the specified (sheetName) sheet in the excel
	 * sheet and returns data for each iteration as an instance of {@link DataTable}
	 * 
	 * @see DataTable
	 * @param dataFileLocation Location where the excel sheet is stored.
	 * @param sheetName        Sheet which contains the test data
	 * @return Object[n][1] containing (n-1) instances of DataTable instances
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public Object[][] readDataFromExcel(String dataFileLocation, String sheetName) {

		LOGGER.info("Initializing Read data from Excel Sheet Located at : " + dataFileLocation + "\nSheet : "
				+ sheetName + "\n");

		boolean isXLSX = false;
		if (dataFileLocation.endsWith(".xlsx")) {
			isXLSX = true;
		} else if (dataFileLocation.endsWith(".xlsm")) {
			isXLSX = true;
		} else if (dataFileLocation.endsWith(".xls")) {
			isXLSX = false;
		} else {
			LOGGER.fatal("Incorrect file format Specified for Excel sheet located at " + dataFileLocation
					+ ". Test will exit.");
			throw new IncorrectExcelFileFormatException(dataFileLocation);
		}

		Workbook workbook;
		Sheet sheet;
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(dataFileLocation));
			if (isXLSX) {
				workbook = new XSSFWorkbook(fileInputStream);

			} else {
				workbook = new HSSFWorkbook(fileInputStream);
			}
			LOGGER.info("Excel opened successfully");
		} catch (Exception e) {
			LOGGER.fatal("Data file not found at " + dataFileLocation + ". Test will exit.");
			throw new DataFileNotFoundException(dataFileLocation);
		}

		/**
		 * Get the Excel Sheet. If sheet name is empty, the first sheet is taken
		 */
		if (!sheetName.equals("")) {
			sheet = workbook.getSheet(sheetName);
		} else {
			sheet = workbook.getSheetAt(0);
		}
		/**
		 * Get List of Heading and save it into an array list, headingList
		 */
		ArrayList<String> headingList = new ArrayList<String>();
		try {
			Iterator<Cell> headingIterator = sheet.getRow(0).cellIterator();
			while (headingIterator.hasNext()) {
				headingList.add(headingIterator.next().getStringCellValue().toUpperCase());
			}
		} catch (NullPointerException e) {
			LOGGER.fatal("Sheet [" + sheetName + "] not found for Excel sheet  located at " + dataFileLocation
					+ ". Test will exit.");
			throw new SheetNotFoundException();
		}

		/**
		 * Iterate through all the rows in the data sheet and get the data to a HashMap.
		 * After each row iteration add the hashMap to ArrayList iterationDataSet
		 */
		iterationDataSet = new ArrayList<LinkedHashMap<String, String>>();
		Iterator<Row> rowIterator = sheet.iterator();
		int i = 0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (i++ != 0) {
				int j = 0;
				LinkedHashMap<String, String> rowValues = new LinkedHashMap<String, String>();
				for (String heading : headingList) {
					Cell cell = row.getCell(j++, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					if (cell == null) {
						rowValues.put(heading, "");
						LOGGER.info("Reading Data --> Paramter: " + heading + " | Value: ");
					} else {
						cell.setCellType(CellType.STRING);
						rowValues.put(heading, cell.getStringCellValue());
						LOGGER.info("Reading Data --> Paramter: " + heading + " | Value: " + cell.getStringCellValue());
					}
				}
				iterationDataSet.add(rowValues);
			}
		}

		try {
			workbook.close();
			LOGGER.info("Excel sheet closed successfully");
		} catch (Exception e) {
			LOGGER.warn("Error in closing Excel sheet located at " + dataFileLocation, e);
		}
		return generateData();
	}

	/**
	 * This method reads the contents in the first sheet in the specified excel
	 * sheet and returns data for each iteration as an instance of {@link DataTable}
	 * 
	 * @see DataTable
	 * @param dataFileLocation Location where the excel sheet is stored.
	 * @return Object[n][1] containing (n-1) instances of DataTable instances
	 */
	public Object[][] readDataFromExcel(String dataFileLocation) {
		return readDataFromExcel(dataFileLocation, "");
	}

	/**
	 * 
	 * 
	 * @param dataFileLocation
	 * @param sheetName
	 * @param value
	 * @param columnName
	 * @param dataToBeEntered
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public void enterDataInRowContainingValue(String dataFileLocation, String sheetName, String value,
			String columnName, String dataToBeEntered) {

		LOGGER.info("Initializing Write data to Excel Sheet Located at : " + dataFileLocation + "\nSheet : " + sheetName
				+ "\n");

		boolean isXLSX = false;
		if (dataFileLocation.endsWith(".xlsx")) {
			isXLSX = true;
		} else if (dataFileLocation.endsWith(".xlsm")) {
			isXLSX = true;
		} else if (dataFileLocation.endsWith(".xls")) {
			isXLSX = false;
		} else {
			LOGGER.fatal("Incorrect file format Specified for Excel sheet located at " + dataFileLocation
					+ ". Test will exit.");
			throw new IncorrectExcelFileFormatException(dataFileLocation);
		}

		Workbook workbook;
		Sheet sheet;
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(dataFileLocation));
			if (isXLSX) {
				workbook = new XSSFWorkbook(fileInputStream);

			} else {
				workbook = new HSSFWorkbook(fileInputStream);
			}
			LOGGER.info("Excel opened successfully");
		} catch (Exception e) {
			LOGGER.fatal("Data file not found at " + dataFileLocation + ". Test will exit.");
			throw new DataFileNotFoundException(dataFileLocation);
		}

		/**
		 * Get the Excel Sheet. If sheet name is empty, the first sheet is taken
		 */
		if (!sheetName.equals("")) {
			sheet = workbook.getSheet(sheetName);
		} else {
			sheet = workbook.getSheetAt(0);
		}

		ArrayList<String> headingList = new ArrayList<String>();
		try {
			Iterator<Cell> headingIterator = sheet.getRow(0).cellIterator();
			while (headingIterator.hasNext()) {
				headingList.add(headingIterator.next().getStringCellValue().toUpperCase());
			}
		} catch (NullPointerException e) {
			LOGGER.fatal("Sheet [" + sheetName + "] not found for Excel sheet  located at " + dataFileLocation
					+ ". Test will exit.");
			throw new SheetNotFoundException();
		}

		int columnNumber = -1;

		try {
			Iterator<Cell> headingIterator = sheet.getRow(0).cellIterator();
			while (headingIterator.hasNext()) {
				Cell cell = headingIterator.next();
				if (cell.getStringCellValue().toUpperCase().equals(columnName.toUpperCase())) {
					columnNumber = cell.getColumnIndex();
				}
			}
		} catch (NullPointerException e) {
			LOGGER.fatal("Sheet [" + sheetName + "] not found for Excel sheet  located at " + dataFileLocation
					+ ". Test will exit.");
			throw new SheetNotFoundException();
		}

		int rowNumber = 0;
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			for (Cell cell : row) {
				if (cell != null) {
					cell.setCellType(CellType.STRING);
					if (cell.getStringCellValue().equals(value)) {
						rowNumber = row.getRowNum();
						break;
					}
				}
			}
		}

		Cell cell = sheet.getRow(rowNumber).createCell(columnNumber);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(dataToBeEntered);

		LOGGER.info("Data [" + dataToBeEntered + "] entered in row [" + rowNumber + "] column [" + columnNumber + "]");

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(dataFileLocation);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			LOGGER.fatal("Error in Write Excel sheet to file at " + dataFileLocation, e);
		} catch (IOException e) {
			LOGGER.fatal("Error in Write Excel sheet to file at " + dataFileLocation, e);
		}

		try {
			workbook.close();
			LOGGER.info("Excel sheet closed successfully");
		} catch (Exception e) {
			LOGGER.warn("Error in closing Excel sheet located at " + dataFileLocation, e);
		}
	}
}
