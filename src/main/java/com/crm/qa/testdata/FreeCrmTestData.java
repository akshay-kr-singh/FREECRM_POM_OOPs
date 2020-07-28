package com.crm.qa.testdata;

import java.util.HashMap;
import java.util.Map;

public class FreeCrmTestData {
	
	public static Map<String, String> getUserLoginInfo() {
		Map<String,String> userLoginInfo = new HashMap<String,String>();
		
		userLoginInfo.put("customer", "akshay.yy180@gmail.com_Test@123");		
		userLoginInfo.put("admin", "adminuser_Test@123");
		userLoginInfo.put("customer_user_name", "Akshay Singh");
		
		return userLoginInfo;
	}

}
