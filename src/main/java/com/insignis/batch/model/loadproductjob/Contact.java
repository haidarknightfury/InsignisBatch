package com.insignis.batch.model.loadproductjob;

import org.springframework.data.annotation.Id;

public class Contact {

	@Id
	private String id;
	private String mobileNumber;
	private ContactType contactType;

	public Contact(String id, String mobileNumber, ContactType contactType) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.contactType = contactType;
	}

	public Contact() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

}
