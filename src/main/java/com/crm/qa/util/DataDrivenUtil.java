package com.crm.qa.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDrivenUtil {
	public static List<Object[]> getNewContacts() {
		XSSFWorkbook workbook = null;
		String pathToExcelFile = System.getProperty("user.dir") + "\\src\\main\\java\\com\\crm\\qa\\testdata\\TestData.xlsx";		 
		try {
			FileInputStream fis = new FileInputStream(pathToExcelFile); 
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int sheetCount = workbook.getNumberOfSheets();
		List<Object[]> contacts = new ArrayList<Object[]>();
		
		for (int i = 0; i < sheetCount; i++) {
			if(workbook.getSheetAt(i).getSheetName().equalsIgnoreCase("NewContacts")) {
				XSSFSheet desiredSheet = workbook.getSheetAt(i);
				Iterator<Row> rowItr = desiredSheet.iterator();
				rowItr.next();
				
				while(rowItr.hasNext()) {
					Row nextRow = rowItr.next();
					Iterator<Cell> cellItr = nextRow.cellIterator();
					List<String> contact = new ArrayList<String>();
					while(cellItr.hasNext()) {
						Cell nextCell = cellItr.next();
						if(nextCell.getCellType().equals(CellType.STRING)) {
							contact.add(nextCell.getStringCellValue());
						}else if(nextCell.getCellType().equals(CellType.NUMERIC)) {
							contact.add(NumberToTextConverter.toText(nextCell.getNumericCellValue()));							
						}						
					}
					
					contacts.add(contact.toArray());					
				}
			}

		}
		
		return contacts;
		
	}

}
