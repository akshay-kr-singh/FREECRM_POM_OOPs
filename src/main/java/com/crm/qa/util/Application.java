package com.crm.qa.util;

public class Application {
	public static void main(String[] args) {
		DataFetcher dataFetcher = new DataFetcher();
		String dataFileLocation = System.getProperty("user.dir") + "//src/main//java//com//crm//qa//testdata//TestData.xlsx";
		String sheetName = "qa";
		Object[][] obj = dataFetcher.readDataFromExcel(dataFileLocation, sheetName);
		DataTable dt = (DataTable) obj[0][0];
		System.out.println(dt.getData("first name"));
	}

}
