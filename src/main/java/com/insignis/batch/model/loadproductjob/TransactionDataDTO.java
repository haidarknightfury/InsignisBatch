package com.insignis.batch.model.loadproductjob;

import java.io.Serializable;
import java.util.Date;

public class TransactionDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String InvoiceNo;
	private String StockCode;
	private String Description;
	private Integer Quantity;
	private Date InvoiceDate;
	private Float UnitPrice;
	private String CustomerID;
	private String Country;

	public TransactionDataDTO() {
	}

	public TransactionDataDTO(String invoiceNo, String stockCode, String description, Integer quantity, Date invoiceDate, Float unitPrice, String customerID, String country) {
		InvoiceNo = invoiceNo;
		StockCode = stockCode;
		Description = description;
		Quantity = quantity;
		InvoiceDate = invoiceDate;
		UnitPrice = unitPrice;
		CustomerID = customerID;
		Country = country;
	}

	public String getInvoiceNo() {
		return InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}

	public String getStockCode() {
		return StockCode;
	}

	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public Date getInvoiceDate() {
		return InvoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		InvoiceDate = invoiceDate;
	}

	public Float getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		UnitPrice = unitPrice;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

}
