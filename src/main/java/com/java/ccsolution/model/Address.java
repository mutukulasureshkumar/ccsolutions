package com.java.ccsolution.model;

public class Address {
	private String FullName;
	private String Address;
	private String ZipCode;
	private String email;
	private CCCheck cardDetails;
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	public CCCheck getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(CCCheck cardDetails) {
		this.cardDetails = cardDetails;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
