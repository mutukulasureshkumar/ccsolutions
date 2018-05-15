package com.java.ccsolution.repository;

import java.util.HashMap;

import org.springframework.context.annotation.Configuration;

import com.java.ccsolution.model.Address;
import com.java.ccsolution.model.CCCheck;

@Configuration
public class CCCheckRepository {
	public static HashMap<Long, Address> CCCheckDB = new HashMap<Long, Address>();
	public static HashMap<Long, Object> CCCheckFraudTrans = new HashMap<Long, Object>();
	public static HashMap<Long, String> STATUS = new HashMap<Long, String>();
	public CCCheckRepository(){
		insertData();
	}
	
	public static void insertData(){
		CCCheck cccheck = new CCCheck();
		cccheck.setNumber(1234234534564567L);
		cccheck.setExpDate("12/2021");
		cccheck.setCvv(234);
		cccheck.setName("M SURESH KUMAR");
		Address address = new Address();
		address.setFullName("M SURESH KUMAR");
		address.setAddress("MIYAPUR, HYDERABAD.");
		address.setZipCode("500068");
		address.setEmail("mutukula.sureshkumar@gmail.com");
		address.setCardDetails(cccheck);
		CCCheckDB.put(cccheck.getNumber(), address);
	}
	
	

}
