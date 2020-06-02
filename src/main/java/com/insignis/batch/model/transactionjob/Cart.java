package com.insignis.batch.model.transactionjob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 8645358424803578666L;

	@Id
	private String id;
	private List<Product> products;
	private BigDecimal total;

	public Cart(String id, List<Product> products, BigDecimal total) {
		this.id = id;
		this.products = products;
		this.total = total;
	}

	public Cart() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
