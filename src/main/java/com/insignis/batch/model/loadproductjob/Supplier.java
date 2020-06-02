package com.insignis.batch.model.loadproductjob;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supplier")
public class Supplier implements Serializable {

	private static final long serialVersionUID = 8346056030239785989L;

	@Id
	private String id;
	private String name;
	private Address address;
	private List<Contact> contacts;

	public Supplier(String id, String name, Address address, List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contacts = contacts;
	}

	public Supplier() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
