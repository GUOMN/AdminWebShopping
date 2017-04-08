package com.model;

public class Invoice {
	private int InvoiceID = 0, userID = 0, InvoiceType = 0;
	private String company = null;
	private String InvoiceDetails = null;

	public int getInvoiceID() {
		return InvoiceID;
	}

	public void setInvoiceID(int invoiceID) {
		InvoiceID = invoiceID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getInvoiceType() {
		return InvoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		InvoiceType = invoiceType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getInvoiceDetails() {
		return InvoiceDetails;
	}

	public void setInvoiceDetails(String invoiceDetails) {
		InvoiceDetails = invoiceDetails;
	}

}
